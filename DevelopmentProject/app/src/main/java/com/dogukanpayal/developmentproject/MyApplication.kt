package com.dogukanpayal.developmentproject

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Uygulama başladığında Dark Mode'u kapat
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
