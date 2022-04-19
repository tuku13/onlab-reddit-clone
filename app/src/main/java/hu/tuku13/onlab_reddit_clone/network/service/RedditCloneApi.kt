package hu.tuku13.onlab_reddit_clone.network.service

import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.network.model.*
import retrofit2.Response
import retrofit2.http.*

interface RedditCloneApi {
    @GET("/groups")
    suspend fun getGroups(): Response<List<GroupDTO>>

    @POST("/login")
    suspend fun login(@Body loginForm: LoginForm): Response<Long>

    @GET("/groups/{group-id}/posts")
    suspend fun getPostsByGroup(@Path("group-id") groupId: Long): Response<List<PostDTO>>

    @GET("/posts/subscribed")
    suspend fun getSubscribedPosts(@Query("user-id") userId: Long): Response<List<PostDTO>>

    @POST("/groups/new")
    suspend fun createGroup(@Body form: CreateGroupForm): Response<Long>

    @FormUrlEncoded
    @POST("/contacts")
    suspend fun getContacts(@Field("user-id") userId: Long): Response<List<Contact>>

    @POST("/messages/send")
    suspend fun sendMessage(@Body form: MessageForm): Response<Long>

    @POST("/messages/")
    suspend fun getMessages(@Body form: GetMessageForm): Response<List<Message>>

    @GET("/users/{id}")
    suspend fun getUserInfo(@Path("id") userId: Long): Response<User>

    @GET("/users/{user-id}/posts")
    suspend fun getUserPost(@Path("user-id") userId: Long): Response<List<PostDTO>>

    @GET("/groups/search")
    suspend fun getGroups(@Query("query") name: String): Response<List<GroupDTO>>

    @POST("/groups/{id}")
    suspend fun getGroup(@Path("id") groupId: Long): Response<GroupDTO>
}