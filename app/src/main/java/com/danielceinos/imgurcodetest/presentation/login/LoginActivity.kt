package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.common.text
import com.danielceinos.imgurcodetest.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

  @Inject
  lateinit var mViewModelFactory: ViewModelProvider.Factory

  private lateinit var mViewModel: LoginViewModel
  private lateinit var mBinding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

    mViewModel = ViewModelProviders.of(this, mViewModelFactory)[LoginViewModel::class.java]
    mViewModel.login("f25705520416faa", "2b1d60f0044e4c9f9bc061b65681612bcff5fd3e")

    mBinding.bLogin.setOnClickListener { mViewModel.login(mBinding.etEmail.text(), mBinding.etPassword.text()) }
  }
}
