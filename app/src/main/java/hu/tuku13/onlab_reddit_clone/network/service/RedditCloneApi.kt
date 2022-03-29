package hu.tuku13.onlab_reddit_clone.network.service

import hu.tuku13.onlab_reddit_clone.network.model.Group
import hu.tuku13.onlab_reddit_clone.network.model.LoginForm
import hu.tuku13.onlab_reddit_clone.util.NetworkResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RedditCloneApi {
    @GET("/groups")
    suspend fun getGroups(): NetworkResponse<List<Group>>

    @POST("/login")
    suspend fun login(@Body loginForm: LoginForm): Response<Int>
}