package com.snow.eagleguardian

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.snow.eagleguardian.utils.LanguageManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

class EagleGuardianApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // 在应用启动时应用语言设置
        runBlocking {
            val currentLanguage = LanguageManager.getCurrentLanguage(this@EagleGuardianApplication).first()
            applyLanguageToContext(this@EagleGuardianApplication, currentLanguage)
        }
    }
    
    private fun applyLanguageToContext(context: Context, language: LanguageManager.Language) {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}
