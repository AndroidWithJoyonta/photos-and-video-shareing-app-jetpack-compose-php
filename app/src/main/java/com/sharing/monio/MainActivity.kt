package com.sharing.monio

import ScreenNavigation
import android.content.ContentValues.TAG
import com.sharing.monio.presentation.screen.dark_and_light_mode.ThemeViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sharing.monio.presentation.ui.theme.MonioTheme
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val themeViewModel by lazy {
            ViewModelProvider(this)[ThemeViewModel::class.java]
        }

        setContent {

            // This must observe the current theme value
            val isDarkTheme = themeViewModel.isDarkTheme.value

            MonioTheme(darkTheme = isDarkTheme) {

                MobileAds.initialize(this){
                    Log.d(TAG,"onCreate : initialize")
                }
                ScreenNavigation(themeViewModel)
            }
        }

    }
}
