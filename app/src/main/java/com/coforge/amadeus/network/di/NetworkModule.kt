package com.coforge.amadeus.network.di

import com.coforge.amadeus.BuildConfig
import com.coforge.amadeus.network.ApiService
import com.coforge.amadeus.network.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).retryOnConnectionFailure(false)

    @Singleton
    @Provides
    fun provideHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addNetworkInterceptor(interceptor).build()
        } else {
            okHttpClientBuilder.build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiServiceImpl(apiServiceImpl: ApiServiceImpl) = apiServiceImpl


    companion object {
        private const val READ_TIMEOUT = 60L
        private const val CONNECT_TIMEOUT = 60L
        private const val BASE_URL = BuildConfig.BASE_URL
    }
}