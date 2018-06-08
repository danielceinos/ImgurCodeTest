package com.danielceinos.imgurcodetest.di.component

import com.danielceinos.imgurcodetest.App
import com.danielceinos.imgurcodetest.di.module.ActivityModule
import com.danielceinos.imgurcodetest.di.module.AppModule
import com.danielceinos.imgurcodetest.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Daniel S on 08/06/2018.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class, ViewModelModule::class])
interface AppComponent {

  fun inject(app: App)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: App): Builder

    fun build(): AppComponent
  }

}
