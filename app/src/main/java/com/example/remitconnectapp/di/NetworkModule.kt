package com.example.remitconnectapp.di

import com.example.remitconnectapp.data.RemittanceParamsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonInstance(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideRemittanceParamsService(okHttpClient: OkHttpClient, gson : Gson): RemittanceParamsService {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://my-json-server.typicode.com/MonecoHQ/fake-api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
            .create(RemittanceParamsService::class.java)
    }

}