package hu.tuku13.onlab_reddit_clone.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.CreateGroupForm
import hu.tuku13.onlab_reddit_clone.network.model.GroupDTO
import hu.tuku13.onlab_reddit_clone.network.model.UserFromDTO
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val api: RedditCloneApi,
    private val authenticationService: AuthenticationService
) {
    val TAG = "Group Repo"

    suspend fun createGroup(form: CreateGroupForm): NetworkResult<Long> {
        return try {
            val groupId = api.createGroup(form).body()
                ?: return NetworkResult.Error(Exception("Error creating group."))

            NetworkResult.Success(groupId)
        } catch (e: Exception) {
            NetworkResult.Error(e)
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

            val userSubscribed = api.isUserAlreadySubscribed(groupId, userId).body()
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
                userId = userId
            ).body() ?: false

            val response = if (userSubscribedResponse) {
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

    suspend fun uploadImage(imageUri: Uri): NetworkResult<String> {
        return try {
            val file = imageUri.toFile()

            Log.d(TAG, "file: $file")

            val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)

            val response = api.uploadImage(requestBody)
            val uploadedImageUrl = response.body()

            if (uploadedImageUrl != null) {
                Log.d(TAG, "uploadedImageUrl: $uploadedImageUrl")
                NetworkResult.Success(uploadedImageUrl)
            } else {
                NetworkResult.Error(Exception("${response.body()}"))
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

}