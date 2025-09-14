package com.snow.eagleguardian.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.util.Log
import com.snow.eagleguardian.R
import com.snow.eagleguardian.ui.icons.CustomIcons
import com.snow.eagleguardian.ui.theme.EagleBlue40
import com.snow.eagleguardian.ui.theme.EagleGreen40
import com.snow.eagleguardian.ui.theme.WarningOrange
import com.snow.eagleguardian.ui.theme.ErrorRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EyeProtectionScreen(
    onBack: () -> Unit = {}
) {
    var distanceMonitorEnabled by remember { mutableStateOf(true) }
    var breakReminderEnabled by remember { mutableStateOf(true) }
    var nightModeEnabled by remember { mutableStateOf(true) }
    var breakInterval by remember { mutableStateOf(20) }
    var breakDuration by remember { mutableStateOf(1) }
    var distanceThreshold by remember { mutableStateOf(30) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.eye_protection)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = EagleBlue40,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 距离监控状态卡片
            item {
                DistanceMonitorCard(
                    isEnabled = distanceMonitorEnabled,
                    onToggle = { distanceMonitorEnabled = !distanceMonitorEnabled },
                    currentDistance = 35, // 模拟当前距离
                    threshold = distanceThreshold
                )
            }
            
            // 休息提醒状态卡片
            item {
                BreakReminderCard(
                    isEnabled = breakReminderEnabled,
                    onToggle = { breakReminderEnabled = !breakReminderEnabled },
                    nextBreakIn = 15, // 模拟下次休息时间
                    interval = breakInterval
                )
            }
            
            // 夜间护眼模式卡片
            item {
                NightModeCard(
                    isEnabled = nightModeEnabled,
                    onToggle = { nightModeEnabled = !nightModeEnabled },
                    isActive = false // 模拟当前是否处于夜间模式
                )
            }
            
            // 设置选项
            item {
                Text(
                    text = stringResource(R.string.settings_options),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                SettingItemCard(
                    title = stringResource(R.string.break_interval_settings),
                    subtitle = stringResource(R.string.break_interval_desc, breakInterval),
                    icon = CustomIcons.TimeManagement,
                    onClick = { 
                        // 打开间隔设置对话框
                        Log.d("EyeProtection", "打开间隔设置对话框")
                    }
                )
            }
            
            item {
                SettingItemCard(
                    title = stringResource(R.string.break_duration_settings),
                    subtitle = stringResource(R.string.break_duration_desc, breakDuration),
                    icon = CustomIcons.BreakReminder,
                    onClick = { 
                        // 打开时长设置对话框
                        Log.d("EyeProtection", "打开时长设置对话框")
                    }
                )
            }
            
            item {
                SettingItemCard(
                    title = stringResource(R.string.distance_threshold_settings),
                    subtitle = stringResource(R.string.distance_warning_threshold, distanceThreshold),
                    icon = CustomIcons.DistanceMonitor,
                    onClick = { 
                        // 打开距离设置对话框
                        Log.d("EyeProtection", "打开距离设置对话框")
                    }
                )
            }
            
            item {
                SettingItemCard(
                    title = stringResource(R.string.night_mode_time),
                    subtitle = stringResource(R.string.night_mode_time_desc),
                    icon = CustomIcons.NightMode,
                    onClick = { 
                        // 打开时间设置对话框
                        Log.d("EyeProtection", "打开时间设置对话框")
                    }
                )
            }
        }
    }
}

@Composable
fun DistanceMonitorCard(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    currentDistance: Int,
    threshold: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) {
                if (currentDistance < threshold) ErrorRed.copy(alpha = 0.1f) else EagleGreen40.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isEnabled) EagleBlue40 else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.distance_monitor),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { onToggle() }
                )
            }
            
            if (isEnabled) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DistanceInfoItem(
                        label = stringResource(R.string.current_distance),
                        value = "${currentDistance}cm",
                        color = if (currentDistance < threshold) ErrorRed else EagleGreen40
                    )
                    DistanceInfoItem(
                        label = stringResource(R.string.warning_threshold),
                        value = "${threshold}cm",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                if (currentDistance < threshold) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.too_close),
                        style = MaterialTheme.typography.bodyMedium,
                        color = ErrorRed,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun BreakReminderCard(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    nextBreakIn: Int,
    interval: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) {
                if (nextBreakIn <= 5) WarningOrange.copy(alpha = 0.1f) else EagleGreen40.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isEnabled) EagleBlue40 else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.break_reminder),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { onToggle() }
                )
            }
            
            if (isEnabled) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BreakInfoItem(
                        label = stringResource(R.string.next_break),
                        value = stringResource(R.string.minutes_after, nextBreakIn),
                        color = if (nextBreakIn <= 5) WarningOrange else EagleGreen40
                    )
                    BreakInfoItem(
                        label = stringResource(R.string.break_interval_label),
                        value = stringResource(R.string.every_minutes, interval),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                if (nextBreakIn <= 5) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.take_break),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarningOrange,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun NightModeCard(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    isActive: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) {
                if (isActive) EagleBlue40.copy(alpha = 0.1f) else EagleGreen40.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isEnabled) EagleBlue40 else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.night_mode),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { onToggle() }
                )
            }
            
            if (isEnabled) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (isActive) stringResource(R.string.night_mode_active) else stringResource(R.string.night_mode_enabled),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isActive) EagleBlue40 else EagleGreen40
                )
            }
        }
    }
}

@Composable
fun DistanceInfoItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun BreakInfoItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SettingItemCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = EagleBlue40
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.enter),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
