package com.snow.eagleguardian

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

/**
 * 应用使用监控服务
 * 监控应用使用情况，执行时间限制策略
 */
class AppUsageMonitorService : Service() {
    
    companion object {
        private const val TAG = "AppUsageMonitorService"
        private const val CHECK_INTERVAL = 1000L // 检查间隔（毫秒）
        private const val CHANNEL_ID = "app_usage_monitor_channel"
        private const val NOTIFICATION_ID = 1001
    }
    
    private var serviceJob: Job? = null
    private var usageStatsManager: UsageStatsManager? = null
    private lateinit var notificationManager: NotificationManager
    
    override fun onCreate() {
        super.onCreate()
        usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        Log.d(TAG, "应用使用监控服务已创建")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startMonitoring()
        return START_STICKY // 服务被杀死后自动重启
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "应用使用监控",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "监控应用使用时间并发送提醒"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun startMonitoring() {
        serviceJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                try {
                    checkAppUsage()
                    delay(CHECK_INTERVAL)
                } catch (e: Exception) {
                    Log.e(TAG, "监控应用使用情况时出错", e)
                }
            }
        }
    }
    
    private fun checkAppUsage() {
        val currentTime = System.currentTimeMillis()
        val startTime = currentTime - TimeUnit.HOURS.toMillis(1) // 检查过去1小时
        
        val usageStats = usageStatsManager?.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            currentTime
        )
        
        usageStats?.let { stats ->
            // 找到当前正在使用的应用
            val currentApp = findCurrentApp(stats)
            currentApp?.let { app ->
                Log.d(TAG, "当前应用: ${app.packageName}, 使用时间: ${app.totalTimeInForeground}ms")
                
                // 检查是否超过时间限制
                checkTimeLimits(app)
            }
        }
    }
    
    private fun findCurrentApp(usageStats: List<UsageStats>): UsageStats? {
        return usageStats.maxByOrNull { it.lastTimeUsed }
    }
    
    private fun checkTimeLimits(app: UsageStats) {
        // 实现时间限制检查逻辑
        val packageName = app.packageName
        val usageTime = app.totalTimeInForeground
        
        // 1. 检查单次使用时间限制（默认30分钟）
        val singleUseLimit = TimeUnit.MINUTES.toMillis(30)
        if (usageTime > singleUseLimit) {
            Log.w(TAG, "应用 $packageName 单次使用时间超过限制")
            // 发送通知或锁定应用
            sendTimeLimitNotification(packageName, "单次使用时间过长")
        }
        
        // 2. 检查每日使用时间限制（默认2小时）
        val dailyLimit = TimeUnit.HOURS.toMillis(2)
        val todayUsage = getTodayUsage(packageName)
        if (todayUsage > dailyLimit) {
            Log.w(TAG, "应用 $packageName 今日使用时间超过限制")
            sendTimeLimitNotification(packageName, "今日使用时间过长")
        }
    }
    
    private fun sendTimeLimitNotification(packageName: String, message: String) {
        // 发送时间限制通知
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("使用时间提醒")
            .setContentText("应用 $packageName $message")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    private fun getTodayUsage(packageName: String): Long {
        // 获取今日使用时间（简化实现）
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val startTime = endTime - TimeUnit.DAYS.toMillis(1)
        
        val usageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )
        
        return usageStats.find { it.packageName == packageName }?.totalTimeInForeground ?: 0L
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceJob?.cancel()
        Log.d(TAG, "应用使用监控服务已销毁")
    }
}
