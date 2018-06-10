package com.danielceinos.imgurcodetest

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.danielceinos.imgurcodetest.presentation.login.LoginActivity
import com.danielceinos.imgurcodetest.presentation.login.LoginViewModel
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


/**
 * Created by Daniel S on 10/06/2018.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

  @Mock
  private lateinit var viewModel: LoginViewModel

  @get:Rule
  val activityTestRule = object : ActivityTestRule<LoginActivity>(LoginActivity::class.java) {
    val app = InstrumentationRegistry.getTargetContext().applicationContext as App

    override fun beforeActivityLaunched() =
        app.setTestActivityInjector(this@LoginActivityTest::inject)

    override fun afterActivityFinished() =
        app.resetTestActivityInjector()
  }

  private fun inject(activity: LoginActivity) {
    activity.mViewModelFactory = createViewModelFactory(viewModel)
  }

  @Test
  fun testAccessDenied() {
    val resultData = Intent()
    resultData.data = Uri.parse("com.danielceinos.imgurcodetest:///oauth2redirect?error=access_denied&state=APPLICATION_STATE")
    activityTestRule.launchActivity(resultData)
    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("access_denied"))).check(matches(isDisplayed()))
  }

  private fun <T : ViewModel> createViewModelFactory(viewModel: T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        if (viewModelClass.isAssignableFrom(viewModel.javaClass)) {
          @Suppress("UNCHECKED_CAST")
          return viewModel as T
        }
        throw IllegalArgumentException("Unknown view model class " + viewModelClass)
      }
    }
  }
}