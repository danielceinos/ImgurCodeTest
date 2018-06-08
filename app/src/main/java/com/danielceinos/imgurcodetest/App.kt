package com.danielceinos.imgurcodetest

import android.app.Activity
import android.app.Application
import com.danielceinos.imgurcodetest.di.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Daniel S on 08/06/2018.
 */
class App : Application(), HasActivityInjector {

  override fun activityInjector(): DispatchingAndroidInjector<Activity> = mAndroidInjector

  @Inject
  lateinit var mAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun onCreate() {
    super.onCreate()
    AppInjector.init(this)
    Stetho.initializeWithDefaults(this);
  }


}