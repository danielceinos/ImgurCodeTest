package com.danielceinos.imgurcodetest

import android.app.Activity
import android.app.Application
import android.support.annotation.VisibleForTesting
import com.danielceinos.imgurcodetest.di.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import dagger.android.HasActivityInjector
import javax.inject.Inject
import javax.inject.Provider

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

  private var activityInjector: DispatchingAndroidInjector<Activity>? = null

//  override fun activityInjector() = activityInjector ?: super.activityInjector()

  @VisibleForTesting
  fun resetTestActivityInjector() {
    activityInjector = null
  }

  @VisibleForTesting
  fun setTestActivityInjector(clazz: Class<out Activity>, injector: AndroidInjector<in Activity>) {
    val factory = AndroidInjector.Factory<Activity> { _ -> AndroidInjector { injector.inject(it) } }
    val provider = Provider<AndroidInjector.Factory<out Activity>> { factory }
    val injectorFactories = mapOf(clazz to provider)

    activityInjector = DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(
        injectorFactories
    )
  }

  @VisibleForTesting
  inline fun <reified T : Activity> setTestActivityInjector(crossinline injector: (T) -> Unit) {
    setTestActivityInjector(T::class.java, AndroidInjector { if (it is T) injector(it) })
  }
}
