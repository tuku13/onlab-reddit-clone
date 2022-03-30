package hu.tuku13.onlab_reddit_clone.repository

import android.util.Log
import hu.tuku13.onlab_reddit_clone.network.model.CreateGroupForm
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val api: RedditCloneApi
){
    val TAG = "Group Repo"

    suspend fun createGroup(form: CreateGroupForm): Long {
        val response = api.createGroup(form)

        return if(response.isSuccessful) {
            response.body()!!
        } else {
            Log.d(TAG, "Error creating group")
            // TODO lekezelni
            -1L
        }
    }

}