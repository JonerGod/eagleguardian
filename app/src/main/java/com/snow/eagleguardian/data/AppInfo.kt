package com.snow.eagleguardian.data

import android.graphics.drawable.Drawable

/**
 * 应用信息数据类
 */
data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    val isSystemApp: Boolean = false,
    val isBlacklisted: Boolean = false,
    val isWhitelisted: Boolean = false,
    val singleUseLimitMinutes: Int = 0, // 单次使用限制（分钟）
    val dailyLimitMinutes: Int = 0, // 每日使用限制（分钟）
    val currentSessionTime: Long = 0, // 当前会话时间（毫秒）
    val todayUsageTime: Long = 0 // 今日已使用时间（毫秒）
)

/**
 * 使用时间统计
 */
data class UsageStats(
    val packageName: String,
    val appName: String,
    val totalTime: Long, // 总使用时间（毫秒）
    val sessionCount: Int, // 会话次数
    val lastUsedTime: Long, // 最后使用时间
    val isCurrentlyActive: Boolean = false // 是否正在使用
)

/**
 * 每日使用报告
 */
data class DailyReport(
    val date: String,
    val totalUsageTime: Long, // 总使用时间（毫秒）
    val appUsageList: List<UsageStats>,
    val breakCount: Int, // 休息次数
    val distanceWarnings: Int, // 距离警告次数
    val nightModeUsage: Long // 夜间模式使用时间
)
