package hu.tuku13.onlab_reddit_clone.service.authentication

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authenticationService: AuthenticationService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = authenticationService.token.value?.let {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        }
        return chain.proceed(request)
    }
}