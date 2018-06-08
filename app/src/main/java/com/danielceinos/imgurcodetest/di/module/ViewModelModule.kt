package com.danielceinos.imgurcodetest.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.danielceinos.imgurcodetest.di.ViewModelFactory
import com.danielceinos.imgurcodetest.di.ViewModelKey
import com.danielceinos.imgurcodetest.presentation.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Daniel S on 08/06/2018.
 */
@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(locationViewModel: LoginViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}