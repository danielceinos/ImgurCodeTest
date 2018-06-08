package com.danielceinos.imgurcodetest.di.module

import com.danielceinos.imgurcodetest.data.ImgurService
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Daniel S on 08/06/2018.
 */
@Module
class AppModule {

  @Provides
  @Singleton
  internal fun provideGson(): Gson {
    return GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()
  }

  @Provides
  @Singleton
  internal fun provideOkhttp(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logginInterceptor = HttpLoggingInterceptor()
    logginInterceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.addInterceptor(logginInterceptor)
    builder.addNetworkInterceptor(StethoInterceptor())
    return builder.build()
  }

  @Provides
  @Singleton
  internal fun provideLocationService(okHttpClient: OkHttpClient, gson: Gson): ImgurService {
    return Retrofit.Builder()
        .baseUrl("https://api.imgur.com/") //TODO gradle
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ImgurService::class.java)
  }
}