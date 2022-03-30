package hu.tuku13.onlab_reddit_clone.repository

import android.util.Log
import hu.tuku13.onlab_reddit_clone.network.model.Post
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import javax.inject.Inject

const val TAG = "PostRepo"

class PostRepository @Inject constructor(
    private val api: RedditCloneApi
){
    suspend fun getPosts(): List<Post> {
        // TODO kicserélni másra ami nem csak 1 FIX csoporthoz az vissza posztot
        val response = api.getPostsByGroup(1)

        return if(response.isSuccessful) {
            response.body()!!
        } else {
            Log.d(TAG, "getPost Error: ${response.errorBody()}")
            emptyList()
        }
    }

    suspend fun getSubscribedPosts(userId: Long): List<Post> {
        val response = api.getSubscribedPosts(userId)

        return if(response.isSuccessful) {
            response.body()!!
        } else {
            Log.d(TAG, "getPost Error: ${response.errorBody()}")
            emptyList()
        }
    }
}