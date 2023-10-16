package com.den.gorobets.giphyseacher.di.modules

import com.den.gorobets.giphyseacher.api.http_engine.EngineService
import com.den.gorobets.giphyseacher.api.http_engine.EngineServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HttpEngineModule {

    @Binds
    @Singleton
    abstract fun bindHttpEngine(httpEngine: EngineServiceImpl): EngineService
}