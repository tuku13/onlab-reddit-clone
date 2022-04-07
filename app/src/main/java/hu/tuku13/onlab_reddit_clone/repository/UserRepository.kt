package hu.tuku13.onlab_reddit_clone.repository

import hu.tuku13.onlab_reddit_clone.network.model.Post
import hu.tuku13.onlab_reddit_clone.network.model.User
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: RedditCloneApi
) {
    suspend fun getUser(userId: Long) : User {
        val response = api.getUserInfo(userId)

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Exception("Invalid user id. ${response.errorBody()}")
        }
    }
}