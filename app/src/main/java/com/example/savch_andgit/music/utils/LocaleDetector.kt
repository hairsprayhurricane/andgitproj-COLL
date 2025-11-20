package com.example.savch_andgit.music.utils

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.*

object LocaleDetector {
    private const val RUSSIAN_LANGUAGE = "ru"
    private val RUSSIAN_SPEAKING_REGIONS = setOf("RU", "BY", "KZ") // Russia, Belarus, Kazakhstan

    fun setLocale(context: Context): Context {
        return updateResourcesLegacy(context, getLocaleForRegion())
    }

    private fun getLocaleForRegion(): Locale {
        val country = Locale.getDefault().country
        return if (RUSSIAN_SPEAKING_REGIONS.contains(country)) {
            Locale(RUSSIAN_LANGUAGE)
        } else {
            Locale.ENGLISH
        }
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else {
            configuration.setLocale(locale)
        }

        return context.createConfigurationContext(configuration)
    }
}
