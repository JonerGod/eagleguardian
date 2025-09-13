package com.snow.eagleguardian.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

object CustomIcons {
    // 应用管理图标
    val Apps: ImageVector
        get() {
            if (_apps != null) {
                return _apps!!
            }
            _apps = materialIcon(name = "Filled.Apps") {
                materialPath {
                    moveTo(4.0f, 6.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(10.0f, 6.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(16.0f, 6.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(4.0f, 12.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(10.0f, 12.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(16.0f, 12.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(4.0f, 18.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(10.0f, 18.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(16.0f, 18.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(4.0f)
                    close()
                }
            }
            return _apps!!
        }
    private var _apps: ImageVector? = null

    // 视力保护图标
    val EyeProtection: ImageVector
        get() {
            if (_eyeProtection != null) {
                return _eyeProtection!!
            }
            _eyeProtection = materialIcon(name = "Filled.EyeProtection") {
                materialPath {
                    // 眼睛轮廓
                    moveTo(12.0f, 4.5f)
                    curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
                    curveTo(2.73f, 16.39f, 7.0f, 19.5f, 12.0f, 19.5f)
                    curveTo(17.0f, 19.5f, 21.27f, 16.39f, 23.0f, 12.0f)
                    curveTo(21.27f, 7.61f, 17.0f, 4.5f, 12.0f, 4.5f)
                    close()
                    moveTo(12.0f, 17.0f)
                    curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                    reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                    reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                    reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                    close()
                    // 瞳孔
                    moveTo(12.0f, 9.0f)
                    curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                    reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                    reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
                    reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
                    close()
                }
            }
            return _eyeProtection!!
        }
    private var _eyeProtection: ImageVector? = null

    // 家长控制图标
    val ParentControl: ImageVector
        get() {
            if (_parentControl != null) {
                return _parentControl!!
            }
            _parentControl = materialIcon(name = "Filled.ParentControl") {
                materialPath {
                    // 家庭图标
                    moveTo(12.0f, 2.0f)
                    lineToRelative(3.09f, 6.26f)
                    lineToRelative(6.91f, 1.01f)
                    lineToRelative(-5.0f, 4.87f)
                    lineToRelative(1.18f, 6.88f)
                    lineToRelative(-6.18f, -3.25f)
                    lineToRelative(-6.18f, 3.25f)
                    lineToRelative(1.18f, -6.88f)
                    lineToRelative(-5.0f, -4.87f)
                    lineToRelative(6.91f, -1.01f)
                    lineToRelative(3.09f, -6.26f)
                    close()
                    // 盾牌
                    moveTo(12.0f, 8.0f)
                    curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                    reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                    reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                    reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                    close()
                }
            }
            return _parentControl!!
        }
    private var _parentControl: ImageVector? = null

    // 使用报告图标
    val UsageReport: ImageVector
        get() {
            if (_usageReport != null) {
                return _usageReport!!
            }
            _usageReport = materialIcon(name = "Filled.UsageReport") {
                materialPath {
                    // 图表
                    moveTo(3.0f, 13.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(-3.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(3.0f)
                    close()
                    moveTo(7.0f, 13.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(-7.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(7.0f)
                    close()
                    moveTo(11.0f, 13.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(-4.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(4.0f)
                    close()
                    moveTo(15.0f, 13.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(2.0f)
                    close()
                    moveTo(19.0f, 13.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(-5.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(5.0f)
                    close()
                    // 坐标轴
                    moveTo(2.0f, 20.0f)
                    horizontalLineToRelative(20.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(-20.0f)
                    verticalLineToRelative(-2.0f)
                    close()
                    moveTo(2.0f, 3.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(20.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineToRelative(-20.0f)
                    close()
                }
            }
            return _usageReport!!
        }
    private var _usageReport: ImageVector? = null

    // 时间管理图标
    val TimeManagement: ImageVector
        get() {
            if (_timeManagement != null) {
                return _timeManagement!!
            }
            _timeManagement = materialIcon(name = "Filled.TimeManagement") {
                materialPath {
                    // 时钟外圈
                    moveTo(12.0f, 2.0f)
                    curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                    reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                    reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                    reflectiveCurveToRelative(-4.48f, -10.0f, -10.0f, -10.0f)
                    close()
                    moveTo(13.0f, 13.0f)
                    horizontalLineToRelative(-3.0f)
                    verticalLineToRelative(-6.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(4.0f)
                    horizontalLineToRelative(1.0f)
                    verticalLineToRelative(2.0f)
                    close()
                    // 时钟指针
                    moveTo(12.0f, 6.0f)
                    lineToRelative(0.0f, 6.0f)
                    lineToRelative(4.0f, 2.0f)
                }
            }
            return _timeManagement!!
        }
    private var _timeManagement: ImageVector? = null

    // 休息提醒图标
    val BreakReminder: ImageVector
        get() {
            if (_breakReminder != null) {
                return _breakReminder!!
            }
            _breakReminder = materialIcon(name = "Filled.BreakReminder") {
                materialPath {
                    // 暂停图标
                    moveTo(6.0f, 19.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-14.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(14.0f)
                    close()
                    moveTo(14.0f, 5.0f)
                    verticalLineToRelative(14.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-14.0f)
                    horizontalLineToRelative(-4.0f)
                    close()
                    // 眼睛
                    moveTo(12.0f, 2.0f)
                    curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                    reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                    reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                    reflectiveCurveToRelative(-4.48f, -10.0f, -10.0f, -10.0f)
                    close()
                }
            }
            return _breakReminder!!
        }
    private var _breakReminder: ImageVector? = null

    // 距离监控图标
    val DistanceMonitor: ImageVector
        get() {
            if (_distanceMonitor != null) {
                return _distanceMonitor!!
            }
            _distanceMonitor = materialIcon(name = "Filled.DistanceMonitor") {
                materialPath {
                    // 眼睛
                    moveTo(12.0f, 4.5f)
                    curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
                    curveTo(2.73f, 16.39f, 7.0f, 19.5f, 12.0f, 19.5f)
                    curveTo(17.0f, 19.5f, 21.27f, 16.39f, 23.0f, 12.0f)
                    curveTo(21.27f, 7.61f, 17.0f, 4.5f, 12.0f, 4.5f)
                    close()
                    moveTo(12.0f, 17.0f)
                    curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                    reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                    reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                    reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                    close()
                    // 距离指示器
                    moveTo(12.0f, 9.0f)
                    moveToRelative(-1.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, 2.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                    close()
                }
            }
            return _distanceMonitor!!
        }
    private var _distanceMonitor: ImageVector? = null

    // 夜间模式图标
    val NightMode: ImageVector
        get() {
            if (_nightMode != null) {
                return _nightMode!!
            }
            _nightMode = materialIcon(name = "Filled.NightMode") {
                materialPath {
                    // 月亮
                    moveTo(12.0f, 3.0f)
                    curveToRelative(-4.97f, 0.0f, -9.0f, 4.03f, -9.0f, 9.0f)
                    reflectiveCurveToRelative(4.03f, 9.0f, 9.0f, 9.0f)
                    reflectiveCurveToRelative(9.0f, -4.03f, 9.0f, -9.0f)
                    reflectiveCurveToRelative(-4.03f, -9.0f, -9.0f, -9.0f)
                    close()
                    moveTo(12.0f, 19.0f)
                    curveToRelative(-3.86f, 0.0f, -7.0f, -3.14f, -7.0f, -7.0f)
                    reflectiveCurveToRelative(3.14f, -7.0f, 7.0f, -7.0f)
                    reflectiveCurveToRelative(7.0f, 3.14f, 7.0f, 7.0f)
                    reflectiveCurveToRelative(-3.14f, 7.0f, -7.0f, 7.0f)
                    close()
                    // 星星
                    moveTo(8.0f, 6.0f)
                    lineToRelative(1.0f, 2.0f)
                    lineToRelative(2.0f, 1.0f)
                    lineToRelative(-2.0f, 1.0f)
                    lineToRelative(-1.0f, 2.0f)
                    lineToRelative(-1.0f, -2.0f)
                    lineToRelative(-2.0f, -1.0f)
                    lineToRelative(2.0f, -1.0f)
                    lineToRelative(1.0f, -2.0f)
                    close()
                }
            }
            return _nightMode!!
        }
    private var _nightMode: ImageVector? = null

    // 云端备份图标
    val CloudBackup: ImageVector
        get() {
            if (_cloudBackup != null) {
                return _cloudBackup!!
            }
            _cloudBackup = materialIcon(name = "Filled.CloudBackup") {
                materialPath {
                    // 云朵
                    moveTo(19.35f, 10.04f)
                    curveTo(18.67f, 6.59f, 15.64f, 4.0f, 12.0f, 4.0f)
                    curveTo(9.11f, 4.0f, 6.6f, 5.64f, 5.35f, 8.04f)
                    curveTo(2.34f, 8.36f, 0.0f, 10.91f, 0.0f, 14.0f)
                    curveToRelative(0.0f, 3.31f, 2.69f, 6.0f, 6.0f, 6.0f)
                    horizontalLineToRelative(13.0f)
                    curveToRelative(2.76f, 0.0f, 5.0f, -2.24f, 5.0f, -5.0f)
                    curveToRelative(0.0f, -2.64f, -2.05f, -4.78f, -4.65f, -4.96f)
                    close()
                    // 上传箭头
                    moveTo(14.0f, 17.0f)
                    lineToRelative(-2.0f, -2.0f)
                    lineToRelative(-2.0f, 2.0f)
                    lineToRelative(2.0f, 2.0f)
                    lineToRelative(2.0f, -2.0f)
                    close()
                    moveTo(12.0f, 9.0f)
                    lineToRelative(0.0f, 6.0f)
                    lineToRelative(2.0f, -2.0f)
                    lineToRelative(2.0f, 2.0f)
                    lineToRelative(-4.0f, 4.0f)
                    lineToRelative(-4.0f, -4.0f)
                    lineToRelative(2.0f, -2.0f)
                    lineToRelative(2.0f, 2.0f)
                    lineToRelative(0.0f, -6.0f)
                    close()
                }
            }
            return _cloudBackup!!
        }
    private var _cloudBackup: ImageVector? = null

    // 小鹰图标
    val Eagle: ImageVector
        get() {
            if (_eagle != null) {
                return _eagle!!
            }
            _eagle = materialIcon(name = "Filled.Eagle") {
                materialPath {
                    // 鹰的轮廓
                    moveTo(12.0f, 2.0f)
                    curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                    reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                    reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                    reflectiveCurveToRelative(-4.48f, -10.0f, -10.0f, -10.0f)
                    close()
                    // 鹰头
                    moveTo(12.0f, 6.0f)
                    curveToRelative(2.21f, 0.0f, 4.0f, 1.79f, 4.0f, 4.0f)
                    reflectiveCurveToRelative(-1.79f, 4.0f, -4.0f, 4.0f)
                    reflectiveCurveToRelative(-4.0f, -1.79f, -4.0f, -4.0f)
                    reflectiveCurveToRelative(1.79f, -4.0f, 4.0f, -4.0f)
                    close()
                    // 翅膀
                    moveTo(8.0f, 14.0f)
                    curveToRelative(0.0f, 2.21f, 1.79f, 4.0f, 4.0f, 4.0f)
                    reflectiveCurveToRelative(4.0f, -1.79f, 4.0f, -4.0f)
                    lineToRelative(-8.0f, 0.0f)
                    close()
                }
            }
            return _eagle!!
        }
    private var _eagle: ImageVector? = null
}
