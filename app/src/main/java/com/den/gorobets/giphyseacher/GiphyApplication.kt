package com.den.gorobets.giphyseacher

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class GiphyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val defaultLocale = Locale.getDefault()
        val defaultLanguage = defaultLocale.language

        DEFAULT_LANGUAGE = defaultLanguage
    }

    companion object {
        var DEFAULT_LANGUAGE: String = "en"
    }
}