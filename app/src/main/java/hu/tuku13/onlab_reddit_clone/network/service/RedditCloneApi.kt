package hu.tuku13.onlab_reddit_clone.network.service

import hu.tuku13.onlab_reddit_clone.network.model.*
import hu.tuku13.onlab_reddit_clone.util.NetworkResponse
import retrofit2.Response
import retrofit2.http.*

interface RedditCloneApi {
    @GET("/groups")
    suspend fun getGroups(): NetworkResponse<List<Group>>

    @POST("/login")
    suspend fun login(@Body loginForm: LoginForm): Response<Long>

    @GET("/groups/{group-id}/posts")
    suspend fun getPostsByGroup(@Path("group-id") groupId: Long): Response<List<Post>>

    @GET("/posts/subscribed")
    suspend fun getSubscribedPosts(@Query("user-id") userId: Long): Response<List<Post>>

    @POST("/groups/new")
    suspend fun createGroup(@Body form: CreateGroupForm): Response<Long>

    @FormUrlEncoded
    @POST("/contacts")
    suspend fun getContacts(@Field("user-id") userId: Long) : Response<List<Contact>>

    @POST("/messages/send")
    suspend fun sendMessage(@Body form: MessageForm) : Response<Long>

    @POST("/messages/")
    suspend fun getMessages(@Body form: GetMessageForm) : Response<List<Message>>
}