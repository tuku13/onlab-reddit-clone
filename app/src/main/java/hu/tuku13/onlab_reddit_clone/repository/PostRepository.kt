package hu.tuku13.onlab_reddit_clone.repository

import android.util.Log
import com.squareup.moshi.JsonDataException
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
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

            if(response.isSuccessful || response.code() == 200) {
                NetworkResult.Success(Unit)
            }

            NetworkResult.Error(Exception("Unable to create new post."))
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}
