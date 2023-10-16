package com.den.gorobets.giphyseacher.di.modules

import com.den.gorobets.giphyseacher.api.ApiHelper
import com.den.gorobets.giphyseacher.api.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiHelperModule {

    @Binds
    @Singleton
    abstract fun bindApiHelper(httpEngine: ApiHelperImpl): ApiHelper
}