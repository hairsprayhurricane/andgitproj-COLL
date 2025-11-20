package com.example.savch_andgit

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.savch_andgit.di.appModule
import com.example.savch_andgit.music.utils.LocaleDetector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleDetector.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleDetector.setLocale(this)
    }
}
