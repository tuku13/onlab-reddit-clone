package hu.tuku13.onlab_reddit_clone.ui.screen.create_group

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.network.model.GroupForm
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "Create group VM"

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val authenticationService: AuthenticationService
) : ViewModel() {

    private var _groupCreated: MutableLiveData<Group?> = MutableLiveData(null)
    val groupCreated: LiveData<Group?>
        get() = _groupCreated

    fun createGroup(groupName: String, description: String, uri: Uri?) {
        if(groupName.isBlank() || description.isBlank()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            var imageUrl = ""

            if (uri != null) {
                when (val response = groupRepository.uploadImage(uri)) {
                    is NetworkResult.Success -> {
                        Log.d("createGroup", "url: ${response.value}")
                        imageUrl = response.value
                    }
                    is NetworkResult.Error -> Log.d(TAG, "${response.exception}")
                }
            }

            when (val response = groupRepository.createGroup(
                GroupForm(
                    groupName = groupName,
                    description = description,
                    imageUrl = imageUrl
                )
            )) {
                is NetworkResult.Success -> getGroup(response.value)
                is NetworkResult.Error -> Log.d(TAG, "Error creating group.")
            }

        }
    }

    private fun getGroup(groupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = groupRepository.getGroup(groupId)) {
                is NetworkResult.Success -> _groupCreated.postValue(response.value)
                is NetworkResult.Error -> Log.d(TAG, "Created not found.")
            }
        }
    }
}