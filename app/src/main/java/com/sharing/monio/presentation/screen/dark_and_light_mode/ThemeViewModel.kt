package com.sharing.monio.presentation.screen.dark_and_light_mode

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    private val _isDarkTheme = mutableStateOf(prefs.getBoolean("dark_theme", false))
    val isDarkTheme: State<Boolean> get() = _isDarkTheme

    fun toggleTheme() {
        val newValue = !_isDarkTheme.value
        _isDarkTheme.value = newValue
        prefs.edit().putBoolean("dark_theme", newValue).apply()
    }
}