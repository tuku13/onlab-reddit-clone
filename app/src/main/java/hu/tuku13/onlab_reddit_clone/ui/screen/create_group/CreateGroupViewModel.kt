package hu.tuku13.onlab_reddit_clone.ui.screen.create_group

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.CreateGroupForm
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "Create group VM"

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val authenticationService: AuthenticationService
) : ViewModel() {

    fun createGroup(groupName: String, description: String, imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val createdGroupId = groupRepository.createGroup(
                CreateGroupForm(
                    userId = authenticationService.userId.value!!,
                    groupName = groupName,
                    description = description,
                    imageUrl = imageUrl
                )
            )

            if(createdGroupId != -1L) {
                Log.d(TAG, "Successful, created with id: $createdGroupId")
            } else {
                Log.d(TAG, "Error creating group")
            }

        }
    }
}