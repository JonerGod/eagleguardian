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
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
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
                    text = "高级设置",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ParentControlItemCard(
                    title = "应用时间限制",
                    subtitle = "管理各应用的使用时间",
                    icon = CustomIcons.TimeManagement,
                    onClick = { 
                        // 打开应用时间限制设置
                        Log.d("ParentControl", "打开应用时间限制设置")
                    }
                )
            }
            
            item {
                ParentControlItemCard(
                    title = "视力保护设置",
                    subtitle = "调整距离监控和休息提醒",
                    icon = CustomIcons.EyeProtection,
                    onClick = { 
                        // 打开视力保护设置
                        Log.d("ParentControl", "打开视力保护设置")
                    }
                )
            }
            
            item {
                ParentControlItemCard(
                    title = "使用统计",
                    subtitle = "查看详细的使用数据分析",
                    icon = CustomIcons.UsageReport,
                    onClick = { 
                        // 打开使用统计
                        Log.d("ParentControl", "打开使用统计")
                    }
                )
            }
            
            item {
                ParentControlItemCard(
                    title = "备份与恢复",
                    subtitle = "备份设置到云端",
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
                text = if (isPasswordSet) "密码保护已启用" else "请设置家长密码以保护设置",
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
                    Text("设置密码")
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
                    text = "查看今日使用情况",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Icon(
                Icons.Default.Settings,
                contentDescription = "查看",
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
                text = if (isEnabled) "远程管理已启用" else "启用后可通过其他设备管理",
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
                        text = "设备管理员",
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
                text = if (isEnabled) "防卸载保护已启用" else "启用后防止应用被卸载",
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
                contentDescription = "进入",
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
        title = { Text("设置家长密码") },
        text = {
            Column {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("密码") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Settings else Icons.Default.Settings,
                                contentDescription = if (passwordVisible) "隐藏密码" else "显示密码"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("确认密码") },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                if (confirmPasswordVisible) Icons.Default.Settings else Icons.Default.Settings,
                                contentDescription = if (confirmPasswordVisible) "隐藏密码" else "显示密码"
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
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
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
        title = { Text("今日使用报告") },
        text = {
            Column {
                UsageReportItem("总使用时间", "1小时30分钟", EagleBlue40)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem("游戏时间", "45分钟", ErrorRed)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem("学习时间", "30分钟", EagleGreen40)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem("休息次数", "3次", WarningOrange)
                Spacer(modifier = Modifier.height(8.dp))
                UsageReportItem("距离警告", "2次", ErrorRed)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("确定")
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
