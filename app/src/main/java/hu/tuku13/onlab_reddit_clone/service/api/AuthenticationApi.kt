package hu.tuku13.onlab_reddit_clone.service.api

import hu.tuku13.onlab_reddit_clone.network.model.LoginForm
import hu.tuku13.onlab_reddit_clone.network.model.RegisterForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("/login")
    suspend fun login(@Body loginForm: LoginForm): Response<Long>

    @POST("/register")
    suspend fun register(@Body form: RegisterForm): Response<Unit>

}