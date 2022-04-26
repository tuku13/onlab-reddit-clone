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

    @GET("/posts/subscribed")
    suspend fun getSubscribedPosts(@Query("user-id") userId: Long): Response<List<PostDTO>>

    @GET("/groups/{group-id}/posts")
    suspend fun getPostsByGroup(@Path("group-id") groupId: Long): Response<List<PostDTO>>

    @GET("/users/{user-id}/posts")
    suspend fun getUserPost(@Path("user-id") userId: Long): Response<List<PostDTO>>

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

    @GET("/groups/search")
    suspend fun getGroups(@Query("query") name: String): Response<List<GroupDTO>>

    @GET("/posts/{post-id}")
    suspend fun getPost(@Path("post-id")postId: Long): Response<PostDTO>

    @GET("/groups/{id}")
    suspend fun getGroup(@Path("id") groupId: Long): Response<GroupDTO>

    @POST("/groups/{group-id}/posts/new")
    suspend fun createPost(@Path("group-id") groupId: Long, @Body form: PostFormDTO): Response<Long>

    @GET("/posts/{post-id}/comments")
    suspend fun getAllComments(@Path("post-id") postId: Long): Response<List<CommentDTO>>

    @GET("/comments/{parent-comment-id}/children")
    suspend fun getChildrenComments(@Path("parent-comment-id") parentCommentId: Long): Response<List<CommentDTO>>
}