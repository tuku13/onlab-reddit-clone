package hu.tuku13.onlab_reddit_clone.repository

import android.util.Log
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.CreateGroupForm
import hu.tuku13.onlab_reddit_clone.network.model.GroupDTO
import hu.tuku13.onlab_reddit_clone.network.model.UserFromDTO
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val api: RedditCloneApi,
    private val authenticationService: AuthenticationService
) {
    val TAG = "Group Repo"

    suspend fun createGroup(form: CreateGroupForm): Long {
        val response = api.createGroup(form)

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.d(TAG, "Error creating group")
            // TODO lekezelni
            -1L
        }
    }

    suspend fun searchGroups(nameQuery: String): List<GroupDTO> {
        val response = api.getGroups(nameQuery)

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            emptyList()
        }
    }

    suspend fun getGroup(groupId: Long): NetworkResult<Group> {
        return try {
            val userId = authenticationService.userId.value
                ?: return NetworkResult.Error(Exception("User is not authenticated."))

            val groupDTO = api.getGroup(groupId).body()
                ?: return NetworkResult.Error(Exception("Group not found."))

            val groupCreator = api.getUserInfo(groupDTO.createdBy).body()
                ?: return NetworkResult.Error(Exception("User who created the group is not found."))

            val userSubscribed = api.isUserAlreadySubscribed(groupId, UserFromDTO(userId)).body()
                ?: false

            NetworkResult.Success(groupDTO.toGroup(groupCreator, userSubscribed))
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    suspend fun subscribe(groupId: Long): NetworkResult<Unit> {
        val userId = authenticationService.userId.value
            ?: return NetworkResult.Error(Exception("User is not authenticated"))

        return try {
            val userSubscribedResponse = api.isUserAlreadySubscribed(
                groupId = groupId,
                form = UserFromDTO(userId)
            )

            val response = if (userSubscribedResponse.isSuccessful) {
                api.unsubscribe(groupId, UserFromDTO(userId))
            } else {
                api.subscribe(groupId, UserFromDTO(userId))
            }

            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(Exception("Unknown error."))
            }

        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

}