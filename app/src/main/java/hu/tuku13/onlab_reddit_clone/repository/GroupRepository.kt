package hu.tuku13.onlab_reddit_clone.repository

import android.net.Uri
import android.util.Log
import hu.tuku13.onlab_reddit_clone.Constants
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.domain.service.FileService
import hu.tuku13.onlab_reddit_clone.network.model.CreateGroupForm
import hu.tuku13.onlab_reddit_clone.network.model.GroupDTO
import hu.tuku13.onlab_reddit_clone.network.model.UserFromDTO
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val api: RedditCloneApi,
    private val authenticationService: AuthenticationService,
    private val fileService: FileService
) {
    val TAG = "Group Repo"

    suspend fun createGroup(form: CreateGroupForm): NetworkResult<Long> {
        return try {
            Log.d("createGroup", "url: ${form.imageUrl}")
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
            val result = fileService.loadImage(imageUri)
            if (result !is NetworkResult.Success) {
                return NetworkResult.Error(Exception("File loading error"))
            }

            val bytes = result.value

            val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), bytes)
            val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "image.png", requestBody)
                .build()

            val request = Request.Builder()
                .url("${Constants.BASE_URL}/images/upload")
                .method("POST", body)
                .addHeader("Content-Type", "image/png")
                .build()

            val client = OkHttpClient().newBuilder().build()

            val url = withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    return@use response.body()?.string()
                }
            }

            return if (url != null) {
                Log.d("uploadImage", "url: $url")
                NetworkResult.Success("${Constants.BASE_URL}/$url")
            } else {
                NetworkResult.Error(Exception(""))
            }
        } catch (e: Exception) {
            Log.d("Exception", "Hiba elkapva: $e")
            NetworkResult.Error(e)
        }
    }

}