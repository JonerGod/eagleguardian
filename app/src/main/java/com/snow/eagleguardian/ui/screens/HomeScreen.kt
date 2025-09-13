package com.snow.eagleguardian.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import com.snow.eagleguardian.R
import com.snow.eagleguardian.ui.icons.CustomIcons
import com.snow.eagleguardian.ui.theme.EagleBlue40
import com.snow.eagleguardian.ui.theme.EagleGreen40
import com.snow.eagleguardian.ui.theme.WarningOrange
import com.snow.eagleguardian.ui.theme.ErrorRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAppManagement: () -> Unit = {},
    onNavigateToEyeProtection: () -> Unit = {},
    onNavigateToParentControl: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToLanguageSettings: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(R.string.welcome_title),
                        fontWeight = FontWeight.Bold
                    ) 
                },
                actions = {
                    IconButton(onClick = onNavigateToLanguageSettings) {
                        Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.language_settings))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = EagleBlue40,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
                    label = { Text(stringResource(R.string.home)) },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(CustomIcons.Apps, contentDescription = stringResource(R.string.app_management_nav)) },
                    label = { Text(stringResource(R.string.app_management_nav)) },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1; onNavigateToAppManagement() }
                )
                NavigationBarItem(
                    icon = { Icon(CustomIcons.EyeProtection, contentDescription = stringResource(R.string.eye_protection_nav)) },
                    label = { Text(stringResource(R.string.eye_protection_nav)) },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2; onNavigateToEyeProtection() }
                )
                NavigationBarItem(
                    icon = { Icon(CustomIcons.ParentControl, contentDescription = stringResource(R.string.parent_control_nav)) },
                    label = { Text(stringResource(R.string.parent_control_nav)) },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3; onNavigateToParentControl() }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 欢迎卡片
            item {
                WelcomeCard()
            }
            
            // 今日使用时间卡片
            item {
                UsageTimeCard(
                    todayUsage = "1小时30分钟",
                    remainingTime = "30分钟",
                    dailyLimit = "2小时"
                )
            }
            
            // 功能快捷入口
            item {
                Text(
                    text = stringResource(R.string.quick_actions),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = stringResource(R.string.app_management),
                        icon = CustomIcons.Apps,
                        color = EagleBlue40,
                        onClick = onNavigateToAppManagement,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = stringResource(R.string.eye_protection),
                        icon = CustomIcons.EyeProtection,
                        color = EagleGreen40,
                        onClick = onNavigateToEyeProtection,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = stringResource(R.string.parent_control),
                        icon = CustomIcons.ParentControl,
                        color = WarningOrange,
                        onClick = onNavigateToParentControl,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = stringResource(R.string.usage_report_title),
                        icon = CustomIcons.UsageReport,
                        color = EagleBlue40,
                        onClick = { 
                            // 实现使用报告功能
                            Log.d("HomeScreen", "打开使用报告")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // 状态提醒
            item {
                StatusAlertsCard()
            }
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EagleBlue40.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                CustomIcons.Eagle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = EagleBlue40
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.welcome_subtitle),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = EagleBlue40
            )
        }
    }
}

@Composable
fun UsageTimeCard(
    todayUsage: String,
    remainingTime: String,
    dailyLimit: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.today_usage),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UsageStatItem(
                    label = "已使用",
                    value = todayUsage,
                    color = EagleBlue40
                )
                UsageStatItem(
                    label = stringResource(R.string.remaining_time),
                    value = remainingTime,
                    color = EagleGreen40
                )
                UsageStatItem(
                    label = "每日限制",
                    value = dailyLimit,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun UsageStatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
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
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
fun StatusAlertsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.status_alerts),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            StatusAlertItem(
                icon = CustomIcons.TimeManagement,
                message = stringResource(R.string.next_break_in_5min),
                color = WarningOrange
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatusAlertItem(
                icon = CustomIcons.DistanceMonitor,
                message = stringResource(R.string.distance_monitor_normal),
                color = EagleGreen40
            )
        }
    }
}

@Composable
fun StatusAlertItem(
    icon: ImageVector,
    message: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
