package com.danielceinos.imgurcodetest.presentation.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.danielceinos.imgurcodetest.R
import com.danielceinos.imgurcodetest.databinding.ActivityLoginBinding
import com.danielceinos.imgurcodetest.presentation.gallery.GalleryActivity
import com.danielceinos.imgurcodetest.presentation.login.LoginViewModel.LoginViewState
import okhttp3.HttpUrl
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

    mBinding.bLogin.setOnClickListener {
      launchOauthIntent()
    }

    if (intent != null && intent.data != null) {
      intent.data.getQueryParameter("code")?.let { mViewModel.login(it) }
      intent.data.getQueryParameter("error")?.let { Snackbar.make(mBinding.root, it, Snackbar.LENGTH_LONG).show() }
    }
  }

  private fun launchOauthIntent() {
    val authorizeUrl = HttpUrl.parse("https://api.imgur.com/oauth2/authorize")
        ?.newBuilder()
        ?.addQueryParameter("client_id", "d94ee637597774f")
        ?.addQueryParameter("response_type", "code")
        ?.addQueryParameter("state", "APPLICATION_STATE")
        ?.build()

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(authorizeUrl?.url().toString())
    startActivity(intent)
  }

  private fun renderViewState(loginViewState: LoginViewState?) {
    loginViewState?.let {
      if (it.success) {
        val intent = Intent(this, GalleryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

      }
      Snackbar.make(mBinding.root, it.msg, Snackbar.LENGTH_LONG).show()
    }
  }
}
