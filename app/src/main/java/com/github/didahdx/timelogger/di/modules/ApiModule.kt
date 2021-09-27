package com.github.didahdx.timelogger.di.modules

import android.app.Application
import com.github.didahdx.timelogger.common.Constant
import com.github.didahdx.timelogger.data.remote.api.LoggerApi
import com.github.didahdx.timelogger.data.repository.ServersRepositoryImpl
import com.github.didahdx.timelogger.domain.repository.ServersRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Daniel Didah on 9/25/21.
 */
@Module
class ApiModule {
    /**
     * Here we return the Gson object
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    /**
     * Here we return the Cache object
     */
    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    /**
     * Here we return the OKhttp object
     */
    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    /**
     * We need the LoggerApi module.
     * For this, We need the Retrofit object, Gson, Cache and OkHttpClient .
     * So we will define the providers for these objects here in this module.
     **/
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): LoggerApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(Constant.ENDPOINT_API)
            .client(okHttpClient)
            .build()
            .create(LoggerApi::class.java)
    }

    @Provides
    @Singleton
    fun serverRepository(loggerApi: LoggerApi): ServersRepository{
        return ServersRepositoryImpl(loggerApi)
    }

}