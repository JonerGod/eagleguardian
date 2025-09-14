package com.snow.eagleguardian.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snow.eagleguardian.MainActivity
import com.snow.eagleguardian.R
import com.snow.eagleguardian.ui.icons.CustomIcons
import com.snow.eagleguardian.ui.theme.EagleBlue40
import com.snow.eagleguardian.ui.theme.EagleGreen40
import com.snow.eagleguardian.utils.LanguageManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSettingsScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showSuccessSnackbar by remember { mutableStateOf(false) }
    
    val currentLanguage by LanguageManager.getCurrentLanguage(context).collectAsStateWithLifecycle(
        initialValue = LanguageManager.getDefaultLanguage()
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.language_settings)) },
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
            // 当前语言显示
            item {
                CurrentLanguageCard(
                    currentLanguage = currentLanguage,
                    onLanguageClick = { showLanguageDialog = true }
                )
            }
            
            // 语言说明
            item {
                LanguageDescriptionCard()
            }
        }
    }
    
    // 语言选择对话框
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = currentLanguage,
            onLanguageSelected = { selectedLanguage ->
                scope.launch {
                    LanguageManager.setLanguage(context, selectedLanguage)
                    showLanguageDialog = false
                    showSuccessSnackbar = true
                    
                    // 延迟重启 Activity 以应用语言更改
                    kotlinx.coroutines.delay(1000)
                    restartActivity(context)
                }
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
    
    // 成功提示
    if (showSuccessSnackbar) {
        LaunchedEffect(showSuccessSnackbar) {
            kotlinx.coroutines.delay(2000)
            showSuccessSnackbar = false
        }
        
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { showSuccessSnackbar = false }) {
                        Text(stringResource(R.string.ok))
                    }
                }
            ) {
                Text(stringResource(R.string.language_changed))
            }
        }
    }
}

@Composable
fun CurrentLanguageCard(
    currentLanguage: LanguageManager.Language,
    onLanguageClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onLanguageClick() },
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
                CustomIcons.TimeManagement,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = EagleBlue40
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.select_language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = EagleBlue40
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentLanguage.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = EagleBlue40
            )
        }
    }
}

@Composable
fun LanguageDescriptionCard() {
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
                text = "语言说明",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "• 简体中文：完整的中文界面\n• English：Complete English interface\n• 日本語：完全な日本語インターフェース",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    currentLanguage: LanguageManager.Language,
    onLanguageSelected: (LanguageManager.Language) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.select_language))
        },
        text = {
            Column {
                LanguageManager.getSupportedLanguages().forEach { language ->
                    LanguageItem(
                        language = language,
                        isSelected = language == currentLanguage,
                        onClick = { onLanguageSelected(language) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
fun LanguageItem(
    language: LanguageManager.Language,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = EagleBlue40
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = language.displayName,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) EagleBlue40 else MaterialTheme.colorScheme.onSurface
        )
    }
}

private fun restartActivity(context: Context) {
    val activity = context as? Activity
    activity?.let {
        val intent = Intent(it, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        it.startActivity(intent)
        it.finish()
    }
}
