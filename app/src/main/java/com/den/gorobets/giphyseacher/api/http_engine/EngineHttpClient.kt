package com.den.gorobets.giphyseacher.api.http_engine

import android.accounts.NetworkErrorException
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

const val API_DOMAIN = "api.giphy.com"
const val API_PATH = "v1/gifs/search"
const val API_KEY = "iUzXS9PhXmtxUTmJunz5LH5Ed6vrbwJ3"

class EngineHttpClient @Inject constructor() {

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    fun create(): HttpClient? =
        try {
            HttpClient(Android) {
                install(HttpRequestRetry) {
                    retryIf { _, response ->
                        !response.status.isSuccess()
                    }
                    retryOnExceptionIf { _, cause ->
                        cause is NetworkErrorException
                    }
                    delayMillis { retry ->
                        retry * 2500L
                    }

                    maxRetries = 5
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d("EngineHttpClient", message)
                        }
                    }
                    level = LogLevel.ALL
                }
                install(HttpTimeout) {
                    connectTimeoutMillis = 30000
                    socketTimeoutMillis = 30000
                    requestTimeoutMillis = 30000
                }
                install(ContentNegotiation) {
                    json(json)
                }
            }
        } catch (_: Exception) {
            null
        }
}