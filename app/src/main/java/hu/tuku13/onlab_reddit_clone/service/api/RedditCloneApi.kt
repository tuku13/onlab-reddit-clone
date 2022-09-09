package hu.tuku13.onlab_reddit_clone.service.api

import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.network.model.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RedditCloneApi {
    @GET("/posts/subscribed")
    suspend fun getSubscribedPosts(@Query("user-id") userId: Long): Response<List<PostDTO>>

    @FormUrlEncoded
    @POST("/contacts")
    suspend fun getContacts(@Field("user-id") userId: Long): Response<List<Contact>>

    @POST("/messages/send")
    suspend fun sendMessage(@Body form: MessageForm): Response<Long>

    @GET("/groups/{group-id}/posts")
    suspend fun getPostsByGroup(@Path("group-id") groupId: Long): Response<List<PostDTO>>

    @GET("/users/{user-id}/posts")
    suspend fun getUserPost(@Path("user-id") userId: Long): Response<List<PostDTO>>

    @POST("/groups/new")
    suspend fun createGroup(@Body form: GroupForm): Response<Long>

    @POST("/messages/")
    suspend fun getMessages(@Body form: GetMessageForm): Response<List<Message>>

    @GET("/groups")
    suspend fun getGroups(): Response<List<GroupDTO>>

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

    @POST("/posts/{post-id}/comments/new")
    suspend fun createComment(@Path("post-id") postId: Long, @Body form: CommentFormDTO): Response<Long>

    @POST("/posts/{post-id}/like")
    suspend fun likePost(@Path("post-id") postId: Long, @Body form: LikeFormDTO): Response<Unit>

    @GET("/users/{user-od}/likes")
    suspend fun getUserLikes(@Path("user-id") userId: Long): Response<List<LikeDTO>>

    @GET("/groups/{group-id}/subscription")
    suspend fun isUserAlreadySubscribed(@Path("group-id") groupId: Long, @Query("userId") userId: Long): Response<Boolean>

    @POST("/groups/{group-id}/subscribe")
    suspend fun subscribe(@Path("group-id") groupId: Long): Response<Unit>

    @POST("/groups/{group-id}/unsubscribe")
    suspend fun unsubscribe(@Path("group-id") groupId: Long): Response<Unit>

    @DELETE("/posts/{post-id}/delete")
    suspend fun deletePost(@Path("post-id") postId: Long, @Query("userId") userId: Long): Response<Unit>

    @Multipart
    @POST("/images/upload")
    suspend fun uploadImage(@Part("file") body: RequestBody): Response<String>

    @PUT("/groups/{id}")
    suspend fun editGroup(@Path("id") groupId: Long, @Body form: GroupForm): Response<Unit>
}