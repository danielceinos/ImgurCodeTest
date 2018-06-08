package com.danielceinos.imgurcodetest.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.danielceinos.imgurcodetest.App
import com.danielceinos.imgurcodetest.di.component.DaggerAppComponent
import dagger.android.AndroidInjection

/**
 * Created by Daniel S on 08/06/2018.
 */
object AppInjector {
  fun init(app: App) {
    DaggerAppComponent.builder().application(app).build().inject(app)
    app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityPaused(activity: Activity?) {
      }

      override fun onActivityResumed(activity: Activity?) {
      }

      override fun onActivityStarted(activity: Activity?) {
      }

      override fun onActivityDestroyed(activity: Activity?) {
      }

      override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
      }

      override fun onActivityStopped(activity: Activity?) {
      }

      override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        handleActivity(activity)
      }
    })
  }

  private fun handleActivity(activity: Activity?) {
    AndroidInjection.inject(activity)
  }
}