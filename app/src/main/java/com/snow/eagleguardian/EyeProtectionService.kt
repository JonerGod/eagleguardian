package com.snow.eagleguardian

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

/**
 * 视力保护服务
 * 监控距离、休息提醒、夜间护眼模式
 */
class EyeProtectionService : Service(), SensorEventListener {
    
    companion object {
        private const val TAG = "EyeProtectionService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "eye_protection_channel"
        private const val CHECK_INTERVAL = 5000L // 检查间隔（毫秒）
    }
    
    private var serviceJob: Job? = null
    private var sensorManager: SensorManager? = null
    private var proximitySensor: Sensor? = null
    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null
    
    // 距离监控相关
    private var isDistanceTooClose = false
    private var lastDistanceWarningTime = 0L
    private val distanceWarningCooldown = 3000L // 3秒冷却时间
    
    // 休息提醒相关
    private var lastBreakTime = 0L
    private var breakInterval = 20 * 60 * 1000L // 20分钟
    private var breakDuration = 60 * 1000L // 1分钟
    private var isInBreakMode = false
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundService()
        initializeSensors()
        startMonitoring()
        Log.d(TAG, "视力保护服务已创建")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "视力保护",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "小鹰守护视力保护服务"
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun startForegroundService() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("小鹰守护")
            .setContentText("视力保护服务正在运行")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
        
        startForeground(NOTIFICATION_ID, notification)
    }
    
    private fun initializeSensors() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        
        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager?.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "EagleGuardian::EyeProtectionWakeLock"
        )
        
        proximitySensor?.let { sensor ->
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    
    private fun startMonitoring() {
        serviceJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                try {
                    checkBreakReminder()
                    delay(CHECK_INTERVAL)
                } catch (e: Exception) {
                    Log.e(TAG, "监控视力保护时出错", e)
                }
            }
        }
    }
    
    private fun checkBreakReminder() {
        val currentTime = System.currentTimeMillis()
        
        // 检查是否需要休息
        if (!isInBreakMode && currentTime - lastBreakTime > breakInterval) {
            startBreakMode()
        }
        
        // 检查休息是否结束
        if (isInBreakMode && currentTime - lastBreakTime > breakDuration) {
            endBreakMode()
        }
    }
    
    private fun startBreakMode() {
        isInBreakMode = true
        lastBreakTime = System.currentTimeMillis()
        
        // 显示休息提醒
        showBreakNotification()
        
        // 锁定屏幕（需要额外权限）
        try {
            val devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val adminComponent = ComponentName(this, DeviceAdminReceiver::class.java)
            
            if (devicePolicyManager.isAdminActive(adminComponent)) {
                devicePolicyManager.lockNow()
                Log.d(TAG, "屏幕已锁定")
            } else {
                Log.w(TAG, "设备管理员权限未激活，无法锁定屏幕")
            }
        } catch (e: Exception) {
            Log.e(TAG, "锁定屏幕失败", e)
        }
        
        Log.d(TAG, "开始休息模式")
    }
    
    private fun endBreakMode() {
        isInBreakMode = false
        lastBreakTime = System.currentTimeMillis()
        
        // 隐藏休息提醒
        hideBreakNotification()
        
        Log.d(TAG, "结束休息模式")
    }
    
    private fun showBreakNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("该休息了！")
            .setContentText("请站起来活动1分钟")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setOngoing(true)
            .build()
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID + 1, notification)
    }
    
    private fun hideBreakNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(NOTIFICATION_ID + 1)
    }
    
    // 距离传感器事件处理
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { sensorEvent ->
            if (sensorEvent.sensor.type == Sensor.TYPE_PROXIMITY) {
                val distance = sensorEvent.values[0]
                val currentTime = System.currentTimeMillis()
                
                // 距离小于5cm认为过近（具体阈值需要根据设备调整）
                val isTooClose = distance < 5.0f
                
                if (isTooClose && !isDistanceTooClose) {
                    isDistanceTooClose = true
                    if (currentTime - lastDistanceWarningTime > distanceWarningCooldown) {
                        showDistanceWarning()
                        lastDistanceWarningTime = currentTime
                    }
                } else if (!isTooClose && isDistanceTooClose) {
                    isDistanceTooClose = false
                    hideDistanceWarning()
                }
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 传感器精度变化处理
    }
    
    private fun showDistanceWarning() {
        // 显示距离过近警告
        Toast.makeText(this, "距离屏幕太近了！请保持30厘米以上距离", Toast.LENGTH_LONG).show()
        Log.w(TAG, "距离过近警告")
    }
    
    private fun hideDistanceWarning() {
        // 隐藏距离警告
        Log.d(TAG, "距离恢复正常")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceJob?.cancel()
        sensorManager?.unregisterListener(this)
        wakeLock?.release()
        Log.d(TAG, "视力保护服务已销毁")
    }
}
