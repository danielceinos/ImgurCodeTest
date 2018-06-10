package com.danielceinos.imgurcodetest.di.module

import android.content.Context
import com.danielceinos.imgurcodetest.App
import com.danielceinos.imgurcodetest.data.AuthTokenInterceptor
import com.danielceinos.imgurcodetest.data.ImgurService
import com.danielceinos.imgurcodetest.data.SharedPreferencesService
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
  internal fun provideOkhttp(authTokenInterceptor: AuthTokenInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logginInterceptor = HttpLoggingInterceptor()
    logginInterceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.addInterceptor(logginInterceptor)
    builder.addNetworkInterceptor(authTokenInterceptor)
    builder.addNetworkInterceptor(StethoInterceptor())
    return builder.build()
  }

  @Provides
  @Singleton
  internal fun provideImgurService(okHttpClient: OkHttpClient, gson: Gson): ImgurService {
    return Retrofit.Builder()
        .baseUrl("https://api.imgur.com/") //TODO gradle
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ImgurService::class.java)
  }

  @Provides
  @Singleton
  internal fun provideSharedPrefencesService(app: App): SharedPreferencesService {
    return SharedPreferencesService(app.getSharedPreferences("prefs",
        Context.MODE_PRIVATE)
    )
  }

  @Provides
  @Singleton
  internal fun provideAuthTokenInterceptor(sharedPreferencesService: SharedPreferencesService): AuthTokenInterceptor {
    return AuthTokenInterceptor(sharedPreferencesService)
  }

}