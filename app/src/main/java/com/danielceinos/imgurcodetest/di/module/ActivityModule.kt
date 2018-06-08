package com.danielceinos.imgurcodetest.di.module

import com.danielceinos.imgurcodetest.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Daniel S on 08/06/2018.
 */
@Module
abstract class ActivityModule {

  @ContributesAndroidInjector
  abstract fun contributeLoginActivity(): LoginActivity
}