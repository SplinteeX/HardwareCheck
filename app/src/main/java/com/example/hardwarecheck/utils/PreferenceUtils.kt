package com.example.hardwarecheck.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {
    private const val PREF_NAME = "hardware_check_prefs"

    private const val KEY_GUIDE_SHOWN = "guide_shown"
    private const val KEY_SAVE_DATA_ENABLED = "save_data_enabled"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun shouldShowGuide(context: Context): Boolean {
        val prefs = getPreferences(context)
        return prefs.getBoolean(KEY_GUIDE_SHOWN, true)
    }

    fun setGuideShown(context: Context, shown: Boolean) {
        val prefs = getPreferences(context)
        prefs.edit().putBoolean(KEY_GUIDE_SHOWN, !shown).apply()
    }

    fun isSaveDataEnabled(context: Context): Boolean {
        val prefs = getPreferences(context)
        return prefs.getBoolean(KEY_SAVE_DATA_ENABLED, false)
    }

    fun setSaveDataEnabled(context: Context, enabled: Boolean) {
        val prefs = getPreferences(context)
        prefs.edit().putBoolean(KEY_SAVE_DATA_ENABLED, enabled).apply()
    }
}