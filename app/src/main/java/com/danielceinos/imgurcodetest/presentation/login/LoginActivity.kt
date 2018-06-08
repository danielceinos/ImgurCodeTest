package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.common.text
import com.danielceinos.imgurcodetest.databinding.ActivityLoginBinding
import com.danielceinos.imgurcodetest.presentation.gallery.GalleryActivity
import com.danielceinos.imgurcodetest.presentation.login.LoginViewModel.LoginViewState
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
    mViewModel.mLoginViewState.observe(this, Observer { renderViewState(it) })
    mBinding.bLogin.setOnClickListener { mViewModel.login(mBinding.etEmail.text(), mBinding.etPassword.text()) }
  }

  private fun renderViewState(loginViewState: LoginViewState?) {
    loginViewState?.let {
      if(it.success) {
        val intent = Intent(this, GalleryActivity::class.java)
        startActivity(intent)
      }
      Snackbar.make(mBinding.root, it.msg, Snackbar.LENGTH_LONG).show()
    }
  }
}
