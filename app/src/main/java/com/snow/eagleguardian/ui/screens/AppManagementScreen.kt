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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import android.util.Log
import com.snow.eagleguardian.R
import com.snow.eagleguardian.data.AppInfo
import com.snow.eagleguardian.ui.icons.CustomIcons
import com.snow.eagleguardian.ui.theme.EagleBlue40
import com.snow.eagleguardian.ui.theme.EagleGreen40
import com.snow.eagleguardian.ui.theme.ErrorRed
import com.snow.eagleguardian.ui.theme.WarningOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppManagementScreen(
    onBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showTimeLimitDialog by remember { mutableStateOf(false) }
    var selectedApp by remember { mutableStateOf<AppInfo?>(null) }
    
    // 模拟应用数据
    val sampleApps = remember {
        listOf(
            AppInfo("com.example.game1", "Game1", null, false, true, false, 30, 60),
            AppInfo("com.example.video", "Video", null, false, true, false, 45, 90),
            AppInfo("com.example.study", "Study", null, false, false, true, 0, 0),
            AppInfo("com.example.reading", "Reader", null, false, false, true, 0, 0)
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_management)) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 标签页
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.weight(1f),
                    text = { 
                        Text(
                            text = stringResource(R.string.blacklist_apps),
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) 
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.weight(1f),
                    text = { 
                        Text(
                            text = stringResource(R.string.whitelist_apps),
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) 
                    }
                )
            }
            
            // 应用列表
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filteredApps = if (selectedTab == 0) {
                    sampleApps.filter { it.isBlacklisted }
                } else {
                    sampleApps.filter { it.isWhitelisted }
                }
                
                items(filteredApps) { app ->
                    AppItemCard(
                        app = app,
                        isBlacklist = selectedTab == 0,
                        onTimeLimitClick = {
                            selectedApp = app
                            showTimeLimitDialog = true
                        }
                    )
                }
            }
        }
    }
    
    // 时间限制设置对话框
    if (showTimeLimitDialog && selectedApp != null) {
        TimeLimitDialog(
            app = selectedApp!!,
            onDismiss = { 
                showTimeLimitDialog = false
                selectedApp = null
            },
            onSave = { singleLimit, dailyLimit ->
                // 保存时间限制设置
                selectedApp?.let { app ->
                    // 这里可以保存到 DataStore 或数据库
                    // 暂时只更新本地状态
                    Log.d("AppManagement", "保存时间限制设置")
                }
                showTimeLimitDialog = false
                selectedApp = null
            }
        )
    }
}

@Composable
fun AppItemCard(
    app: AppInfo,
    isBlacklist: Boolean,
    onTimeLimitClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onTimeLimitClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isBlacklist) {
                ErrorRed.copy(alpha = 0.1f)
            } else {
                EagleGreen40.copy(alpha = 0.1f)
            }
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 应用图标占位符
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (isBlacklist) ErrorRed else EagleGreen40,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isBlacklist) Icons.Default.Close else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = app.appName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = app.packageName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                if (isBlacklist) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        if (app.singleUseLimitMinutes > 0) {
                            TimeLimitChip(
                                label = stringResource(R.string.single_use_limit, app.singleUseLimitMinutes),
                                color = WarningOrange
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        if (app.dailyLimitMinutes > 0) {
                            TimeLimitChip(
                                label = stringResource(R.string.daily_use_limit, app.dailyLimitMinutes),
                                color = ErrorRed
                            )
                        }
                    }
                }
            }
            
            Icon(
                Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings_icon),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun TimeLimitChip(
    label: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .background(
                color.copy(alpha = 0.2f),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TimeLimitDialog(
    app: AppInfo,
    onDismiss: () -> Unit,
    onSave: (Int, Int) -> Unit
) {
    var singleLimit by remember { mutableStateOf(app.singleUseLimitMinutes.toString()) }
    var dailyLimit by remember { mutableStateOf(app.dailyLimitMinutes.toString()) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.set_time_limit_for, app.appName)) },
        text = {
            Column {
                OutlinedTextField(
                    value = singleLimit,
                    onValueChange = { singleLimit = it },
                    label = { Text(stringResource(R.string.single_use_limit_minutes)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = dailyLimit,
                    onValueChange = { dailyLimit = it },
                    label = { Text(stringResource(R.string.daily_use_limit_minutes)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val single = singleLimit.toIntOrNull() ?: 0
                    val daily = dailyLimit.toIntOrNull() ?: 0
                    onSave(single, daily)
                }
            ) {
                Text(stringResource(R.string.save_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button))
            }
        }
    )
}
