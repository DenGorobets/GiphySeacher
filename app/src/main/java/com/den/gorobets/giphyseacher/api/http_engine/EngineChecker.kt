package com.den.gorobets.giphyseacher.api.http_engine

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import javax.inject.Inject

class EngineChecker @Inject constructor(
    val client: EngineHttpClient,
) {

    suspend inline fun <reified T> performHttpGet(
        path: String,
        parameters: Map<String, Any>,
        errorMessage: String
    ): T? {
        try {
            val response = client.create()?.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = API_DOMAIN
                    path(path)
                    parameter("api_key", API_KEY)
                    parameters.forEach { (key, value) ->
                        parameter(key, value.toString())
                    }
                }
                contentType(ContentType.Application.Json)
            }

            return response?.let {
                if (it.status.isSuccess()) {
                    it.body<T>()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: RedirectResponseException) {
            e.printStackTrace()
        } catch (e: ClientRequestException) {
            e.printStackTrace()
        } catch (e: ServerResponseException) {
            e.printStackTrace()
        }
        Log.d("PerformHttpGet", errorMessage)
        return null
    }
}
