package hu.tuku13.onlab_reddit_clone.service.authentication

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class LogoutInterceptor(private val authenticationService: AuthenticationService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        Log.d("LogoutInterceptor", "intercept()")

        if (response.code() == 403) {
            Log.d("LogoutInterceptor", "Token is expired or invalid.")
            authenticationService.logout()
        }

        return response
    }
}