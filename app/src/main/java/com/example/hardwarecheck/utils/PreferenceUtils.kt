package com.example.hardwarecheck.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale
import androidx.core.content.edit

object PreferenceHelper {
    private const val PREF_NAME = "hardware_check_prefs"

    private const val KEY_GUIDE_SHOWN = "guide_shown"
    private const val KEY_SAVE_DATA_ENABLED = "save_data_enabled"

    private const val APP_LANGUAGE_KEY = "app_language"

    fun setAppLanguage(context: Context, languageCode: String) {
        context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            .edit() {
                putString(APP_LANGUAGE_KEY, languageCode)
            }

        applyLocale(context, languageCode)
    }

    fun getAppLanguage(context: Context): String {
        return context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            .getString(APP_LANGUAGE_KEY, Locale.getDefault().language)
            ?: Locale.getDefault().language
    }

    fun applyLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun shouldShowGuide(context: Context): Boolean {
        val prefs = getPreferences(context)
        return prefs.getBoolean(KEY_GUIDE_SHOWN, true)
    }

    fun setGuideShown(context: Context, shown: Boolean) {
        val prefs = getPreferences(context)
        prefs.edit() { putBoolean(KEY_GUIDE_SHOWN, !shown) }
    }

    fun isSaveDataEnabled(context: Context): Boolean {
        val prefs = getPreferences(context)
        return prefs.getBoolean(KEY_SAVE_DATA_ENABLED, false)
    }

    fun setSaveDataEnabled(context: Context, enabled: Boolean) {
        val prefs = getPreferences(context)
        prefs.edit() { putBoolean(KEY_SAVE_DATA_ENABLED, enabled) }
    }
}