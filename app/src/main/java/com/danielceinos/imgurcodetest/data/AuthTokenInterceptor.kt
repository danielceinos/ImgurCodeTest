package com.danielceinos.imgurcodetest.data

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Daniel S on 10/06/2018.
 */
@Singleton
class AuthTokenInterceptor
@Inject constructor(private val sharedPreferencesService: SharedPreferencesService) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()

    val request = original.newBuilder()
    addAuth(request)

    return chain.proceed(request.build())
  }

  private fun addAuth(request: Request.Builder) {
    val authToken = sharedPreferencesService.getToken()
    if (authToken != null && authToken != "") {
      request.addHeader("Authorization", "Bearer $authToken")
    }
  }
}
