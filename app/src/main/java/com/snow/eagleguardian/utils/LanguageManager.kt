package com.snow.eagleguardian.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

object LanguageManager {
    
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "language_settings")
    
    private val LANGUAGE_KEY = stringPreferencesKey("selected_language")
    
    enum class Language(val code: String, val displayName: String) {
        CHINESE("zh", "简体中文"),
        ENGLISH("en", "English"),
        JAPANESE("ja", "日本語")
    }
    
    /**
     * 获取当前系统语言
     */
    fun getSystemLanguage(): Language {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale.getDefault()
        } else {
            @Suppress("DEPRECATION")
            Locale.getDefault()
        }
        
        return when (locale.language) {
            "zh" -> Language.CHINESE
            "en" -> Language.ENGLISH
            "ja" -> Language.JAPANESE
            else -> Language.ENGLISH // 默认英语
        }
    }
    
    /**
     * 获取应用默认语言（始终为英文）
     */
    fun getDefaultLanguage(): Language {
        return Language.ENGLISH
    }
    
    /**
     * 获取当前选择的语言
     */
    fun getCurrentLanguage(context: Context): Flow<Language> {
        return context.dataStore.data.map { preferences ->
            val languageCode = preferences[LANGUAGE_KEY] ?: getDefaultLanguage().code
            Language.values().find { it.code == languageCode } ?: getDefaultLanguage()
        }
    }
    
    /**
     * 设置语言
     */
    suspend fun setLanguage(context: Context, language: Language) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }
    
    /**
     * 应用语言设置到 Context
     */
    fun applyLanguage(context: Context, language: Language): Context {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
    
    /**
     * 获取所有支持的语言列表
     */
    fun getSupportedLanguages(): List<Language> {
        return Language.values().toList()
    }
}
