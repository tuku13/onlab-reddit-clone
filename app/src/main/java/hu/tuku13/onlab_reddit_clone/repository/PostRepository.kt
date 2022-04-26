package hu.tuku13.onlab_reddit_clone.repository

import android.util.Log
import com.squareup.moshi.JsonDataException
import hu.tuku13.onlab_reddit_clone.domain.model.Comment
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.CommentDTO
import hu.tuku13.onlab_reddit_clone.network.model.CommentFormDTO
import hu.tuku13.onlab_reddit_clone.network.model.PostDTO
import hu.tuku13.onlab_reddit_clone.network.model.PostFormDTO
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: RedditCloneApi,
    private val authenticationService: AuthenticationService
) {
    val TAG = "PostRepo"

    suspend fun getUserPosts(userId: Long): NetworkResult<List<Post>> {
        return try {
            val postResponse = api.getUserPost(userId)
            if (!postResponse.isSuccessful) {
                NetworkResult.Error(Exception("Post not found."))
            }

            val userResponse = api.getUserInfo(userId)
            if (!userResponse.isSuccessful) {
                NetworkResult.Error(Exception("User not found."))
            }

            NetworkResult.Success(postResponse.body()!!.map { it.toPost(userResponse.body()!!) })
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    suspend fun getPost(postId: Long): NetworkResult<Post> {
        return try {
            val postResponse = api.getPost(postId)

            if (!postResponse.isSuccessful) {
                NetworkResult.Error(Exception("Post not found."))
            }
            val postDTO = postResponse.body()!!

            val userResponse = api.getUserInfo(postDTO.userId)

            if (!userResponse.isSuccessful) {
                NetworkResult.Error(Exception("User not found."))
            }
            val user = userResponse.body()!!

            val post = postDTO.toPost(user)

            return NetworkResult.Success(post)
        } catch (e: Exception) {
            Log.d(TAG, "$e")
            NetworkResult.Error(e)
        }
    }

    suspend fun getPosts(groupId: Long): NetworkResult<List<Post>> {
        return try {
            val postResponse = api.getPostsByGroup(groupId)
            if (!postResponse.isSuccessful) {
                NetworkResult.Error(Exception("Post not found."))
            }

            val postDTOs = postResponse.body()?.toMutableList() ?: return NetworkResult.Error(
                Exception("Post not found.")
            )
            val users: MutableMap<Long, User> = mutableMapOf()
            val postDTOsToRemove: MutableList<PostDTO> = mutableListOf()

            postDTOs.forEach {
                if (users.containsKey(it.userId)) {
                    return@forEach
                }
                val userResponse = api.getUserInfo(it.userId)
                if (userResponse.isSuccessful && userResponse.body() != null) {
                    users[it.userId] = userResponse.body()!!
                } else {
                    postDTOsToRemove.add(it)
                }
            }

            postDTOs.removeAll(postDTOsToRemove)
            NetworkResult.Success(postResponse.body()!!.map { it.toPost(users[it.userId]!!) })
        } catch (e: Exception) {
            Log.d(TAG, "$e")
            NetworkResult.Error(e)
        }
    }

    suspend fun getCommentsAtPost(postId: Long): NetworkResult<List<Comment>> {
        return try {
            val commentResponse = api.getAllComments(postId)

            if (!commentResponse.isSuccessful) {
                NetworkResult.Error(Exception("Post not found."))
            }

            val commentDTOs = commentResponse.body()!!
            val comments = convertCommentDTOsToComments(commentDTOs)

            NetworkResult.Success(comments)
        } catch (e: Exception) {
            Log.d(TAG, "$e")
            NetworkResult.Error(e)
        }
    }

    suspend fun getChildrenComments(parentCommentId: Long): NetworkResult<List<Comment>> {
        return try {
            val commentResponse = api.getChildrenComments(parentCommentId)

            if (!commentResponse.isSuccessful) {
                NetworkResult.Error(Exception("Post not found."))
            }

            val commentDTOs = commentResponse.body()!!
            val comments = convertCommentDTOsToComments(commentDTOs)

            NetworkResult.Success(comments)
        } catch (e: Exception) {
            Log.d(TAG, "$e")
            NetworkResult.Error(e)
        }
    }

    private suspend fun convertCommentDTOsToComments(commentDTOs: List<CommentDTO>): List<Comment> {
        val userIds = commentDTOs.map { it.postedBy }.toSet()
        val users = mutableListOf<User>()

        userIds.forEach { userId ->
            val userResponse = api.getUserInfo(userId)

            if (userResponse.isSuccessful) {
                users.add(userResponse.body()!!)
            }
        }

        val comments = mutableListOf<Comment>()
        commentDTOs.forEach { commentDTO ->
            try {
                val user = users.first { it.id == commentDTO.postedBy }
                comments.add(commentDTO.toComment(user))
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
        return comments
    }

    suspend fun getSubscribedPosts(userId: Long): NetworkResult<List<Post>> {
        return try {
            val postResponse = api.getSubscribedPosts(userId)
            if (!postResponse.isSuccessful) {
                NetworkResult.Error(Exception("Post not found."))
            }

            val userResponse = api.getUserInfo(userId)
            if (!userResponse.isSuccessful) {
                NetworkResult.Error(Exception("User not found."))
            }

            NetworkResult.Success(postResponse.body()!!.map { it.toPost(userResponse.body()!!) })
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    suspend fun createComment(
        text: String,
        postId: Long,
        parentCommentId: Long?
    ): NetworkResult<Long> {
        if (authenticationService.userId.value == null) {
            return NetworkResult.Error(Exception("User is not authenticated."))
        }

        val form = CommentFormDTO(
            userId = authenticationService.userId.value ?: 0L,
            text = text,
            parentCommentId = parentCommentId
        )
        return try {
            val response = api.createComment(postId, form)

            if (!response.isSuccessful) {
                NetworkResult.Error(Exception("Unknown error."))
            }

            NetworkResult.Success(response.body()!!)
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    suspend fun createPost(groupId: Long, title: String, text: String): NetworkResult<Unit> {
        return try {
            val response = api.createPost(
                groupId = groupId,
                form = PostFormDTO(
                    userId = authenticationService.userId.value ?: 0L,
                    title = title,
                    text = text
                )
            )

            if (response.isSuccessful || response.code() == 200) {
                return NetworkResult.Success(Unit)
            }

            NetworkResult.Error(Exception("Unable to create new post."))
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}
