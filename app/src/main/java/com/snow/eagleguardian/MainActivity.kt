package com.snow.eagleguardian

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snow.eagleguardian.ui.screens.*
import com.snow.eagleguardian.ui.theme.EagleGuardianTheme
import com.snow.eagleguardian.utils.LanguageManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EagleGuardianTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    EagleGuardianApp()
                }
            }
        }
    }
}

@Composable
fun EagleGuardianApp() {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf("home") }
    
    // 监听语言变化
    val currentLanguage by LanguageManager.getCurrentLanguage(context).collectAsStateWithLifecycle(
        initialValue = LanguageManager.getDefaultLanguage()
    )
    
    // 应用语言设置
    val localizedContext = remember(currentLanguage) {
        LanguageManager.applyLanguage(context, currentLanguage)
    }
    
    when (currentScreen) {
        "home" -> HomeScreen(
            onNavigateToAppManagement = { currentScreen = "app_management" },
            onNavigateToEyeProtection = { currentScreen = "eye_protection" },
            onNavigateToParentControl = { currentScreen = "parent_control" },
            onNavigateToLanguageSettings = { currentScreen = "language_settings" }
        )
        "app_management" -> AppManagementScreen(
            onBack = { currentScreen = "home" }
        )
        "eye_protection" -> EyeProtectionScreen(
            onBack = { currentScreen = "home" }
        )
        "parent_control" -> ParentControlScreen(
            onBack = { currentScreen = "home" }
        )
        "language_settings" -> LanguageSettingsScreen(
            onBack = { currentScreen = "home" }
        )
    }
}