package com.snow.eagleguardian.data

/**
 * 用户设置数据类
 */
data class UserSettings(
    val parentPassword: String = "",
    val isPasswordSet: Boolean = false,
    val dailyTimeLimit: Int = 120, // 每日总时间限制（分钟）
    val breakInterval: Int = 20, // 休息间隔（分钟）
    val breakDuration: Int = 1, // 休息时长（分钟）
    val distanceThreshold: Int = 30, // 距离阈值（厘米）
    val isDistanceMonitorEnabled: Boolean = true,
    val isBreakReminderEnabled: Boolean = true,
    val isNightModeEnabled: Boolean = true,
    val nightModeStartHour: Int = 20, // 夜间模式开始时间（小时）
    val nightModeEndHour: Int = 7, // 夜间模式结束时间（小时）
    val isDeviceAdminEnabled: Boolean = false,
    val isRemoteManagementEnabled: Boolean = false
)

/**
 * 视力保护设置
 */
data class EyeProtectionSettings(
    val isDistanceMonitorEnabled: Boolean = true,
    val distanceThreshold: Int = 30, // 厘米
    val isBreakReminderEnabled: Boolean = true,
    val breakInterval: Int = 20, // 分钟
    val breakDuration: Int = 1, // 分钟
    val isNightModeEnabled: Boolean = true,
    val nightModeStartHour: Int = 20,
    val nightModeEndHour: Int = 7,
    val brightnessReduction: Float = 0.3f, // 夜间模式亮度降低比例
    val blueLightFilter: Float = 0.5f // 蓝光过滤强度
)

/**
 * 应用管理设置
 */
data class AppManagementSettings(
    val blacklistedApps: Set<String> = emptySet(), // 黑名单应用包名
    val whitelistedApps: Set<String> = emptySet(), // 白名单应用包名
    val appTimeLimits: Map<String, Int> = emptyMap(), // 应用时间限制（分钟）
    val singleUseLimits: Map<String, Int> = emptyMap() // 单次使用限制（分钟）
)
