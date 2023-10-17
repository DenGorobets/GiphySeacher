package com.den.gorobets.giphyseacher

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class GiphyApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        val defaultLocale = Locale.getDefault()
        val defaultLanguage = defaultLocale.language

        DEFAULT_LANGUAGE = defaultLanguage
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .strongReferencesEnabled(true)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir)
                    .maxSizePercent(0.05)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }

    companion object {
        var DEFAULT_LANGUAGE: String = "en"
    }
}