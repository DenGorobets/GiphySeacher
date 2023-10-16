package com.den.gorobets.giphyseacher.utils

sealed class Resource<T>(val data: T?, val message: String?) {
    class Loading<T>(data: T? = null) : Resource<T>(data, null)
    class Success<T>(data: T?) : Resource<T>(data = data, null)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data = data, message)
}