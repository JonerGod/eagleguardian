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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun ParentControlScreen(
    onBack: () -> Unit = {}
) {
    var showPasswordDialog by remember { mutableStateOf(false) }
    var isPasswordSet by remember { mutableStateOf(false) }
    var showUsageReport by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.parent_control)) },
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
            // 密码保护状态
            item {
                PasswordProtectionCard(
                    isPasswordSet = isPasswordSet,
                    onSetPassword = { showPasswordDialog = true }
                )
            }
            
            // 使用报告
            item {
                UsageReportCard(
                    onViewReport = { showUsageReport = true }
                )
            }
            
            // 远程管理
            item {
                RemoteManagementCard()
            }
            
            // 设备管理
            item {
                DeviceManagementCard()
            }
            
            // 设置选项
            item {
                Text(
                    text = stringResource(R.string.advanced_settings),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ParentControlItemCard(
                    title = stringResource(R.string.app_time_limits_title),
                    subtitle = stringResource(R.string.manage_app_usage_time),
                    icon = CustomIcons.TimeManagement,
                    onClick = { 
                        // 打开应用时间限制设置
                        Log.d("ParentControl", "打开应用时间限制设置")
                    }
                )
            }
            
            item {
                ParentControlItemCard(
                    title = stringResource(R.string.eye_protection_settings_title),
                    subtitle = stringResource(R.string.adjust_distance_monitor),
                    icon = CustomIcons.EyeProtection,
                    onClick = { 
                        // 打开视力保护设置
                        Log.d("ParentControl", "打开视力保护设置")
                    }
                )
            }
            
            item {
                ParentControlItemCard(
                    title = stringResource(R.string.usage_statistics_title),
                    subtitle = stringResource(R.string.view_detailed_analytics),
                    icon = CustomIcons.UsageReport,
                    onClick = { 
                        // 打开使用统计
                        Log.d("ParentControl", "打开使用统计")
                    }
                )
            }
            
            item {
                ParentControlItemCard(
                    title = stringResource(R.string.backup_restore_title),
                    subtitle = stringResource(R.string.backup_settings_cloud),
                    icon = CustomIcons.CloudBackup,
                    onClick = { 
                        // 打开备份设置
                        Log.d("ParentControl", "打开备份设置")
                    }
                )
            }
        }
    }
    
    // 密码设置对话框
    if (showPasswordDialog) {
        PasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onConfirm = { password ->
                isPasswordSet = true
                showPasswordDialog = false
            }
        )
    }
    
    // 使用报告对话框
    if (showUsageReport) {
        UsageReportDialog(
            onDismiss = { showUsageReport = false }
        )
    }
}

@Composable
fun PasswordProtectionCard(
    isPasswordSet: Boolean,
    onSetPassword: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isPasswordSet) EagleGreen40.copy(alpha = 0.1f) else WarningOrange.copy(alpha = 0.1f)
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
                        Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isPasswordSet) EagleGreen40 else WarningOrange
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.password_settings),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    if (isPasswordSet) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isPasswordSet) EagleGreen40 else WarningOrange
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (isPasswordSet) stringResource(R.string.password_protection_enabled) else stringResource(R.string.password_protection_prompt),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isPasswordSet) EagleGreen40 else WarningOrange
            )
            
            if (!isPasswordSet) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onSetPassword,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarningOrange
                    )
                ) {
                    Text(stringResource(R.string.set_password_button))
                }
            }
        }
    }
}

@Composable
fun UsageReportCard(
    onViewReport: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onViewReport() },
        colors = CardDefaults.cardColors(
            containerColor = EagleBlue40.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = EagleBlue40
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.usage_report),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.view_today_usage),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Icon(
                Icons.Default.Settings,
                contentDescription = stringResource(R.string.view_action),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun RemoteManagementCard() {
    var isEnabled by remember { mutableStateOf(false) }
    
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = EagleBlue40
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.remote_management),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isEnabled = !isEnabled }
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (isEnabled) stringResource(R.string.remote_management_enabled) else stringResource(R.string.remote_management_prompt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun DeviceManagementCard() {
    var isEnabled by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) EagleGreen40.copy(alpha = 0.1f) else ErrorRed.copy(alpha = 0.1f)
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
                        Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isEnabled) EagleGreen40 else ErrorRed
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.device_admin_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isEnabled = !isEnabled }
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (isEnabled) stringResource(R.string.uninstall_protection_enabled) else stringResource(R.string.uninstall_protection_prompt),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isEnabled) EagleGreen40 else ErrorRed
            )
        }
    }
}

@Composable
fun ParentControlItemCard(
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
                Icons.Default.Settings,
                contentDescription = stringResource(R.string.enter_action),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun PasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.set_parent_password)) },
        text = {
            Column {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password_label)) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Settings else Icons.Default.Settings,
                                contentDescription = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(stringResource(R.string.confirm_password_label)) },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                if (confirmPasswordVisible) Icons.Default.Settings else Icons.Default.Settings,
                                contentDescription = if (confirmPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (password == confirmPassword && password.isNotEmpty()) {
                        onConfirm(password)
                    }
                },
                enabled = password == confirmPassword && password.isNotEmpty()
            ) {
                Text(stringResource(R.string.confirm_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_button))
            }
        }
    )
}

@Composable
fun UsageReportDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.today_usage_report)) },
        text = {
            Column {
                UsageReportItem(stringResource(R.string.total_usage_time), stringResource(R.string.usage_time_sample), EagleBlue40)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem(stringResource(R.string.game_time), stringResource(R.string.game_time_sample), ErrorRed)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem(stringResource(R.string.study_time), stringResource(R.string.study_time_sample), EagleGreen40)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem(stringResource(R.string.break_count), stringResource(R.string.break_count_sample), WarningOrange)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem(stringResource(R.string.distance_warnings), stringResource(R.string.distance_warnings_sample), ErrorRed)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.confirm_button))
            }
        }
    )
}

@Composable
fun UsageReportItem(
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
}
