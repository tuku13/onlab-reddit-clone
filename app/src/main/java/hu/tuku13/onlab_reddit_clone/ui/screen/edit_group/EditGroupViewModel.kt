package hu.tuku13.onlab_reddit_clone.ui.screen.edit_group

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private val TAG = "EditGroupVM"
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _group: MutableLiveData<Group?> = MutableLiveData(null)
    val group: LiveData<Group?>
        get() = _group

    fun loadGroup(groupId: Long) {
        viewModelScope.launch(dispatcher) {
            when (val response = groupRepository.getGroup(groupId)) {
                is NetworkResult.Success -> _group.postValue(response.value)
                is NetworkResult.Error -> Log.d(TAG, "loadGroup ${response.exception}")
            }
        }
    }

    fun editGroup(groupId: Long, groupName: String?, description: String?, imageUri: Uri?) {
        viewModelScope.launch(dispatcher) {
            when (val response = groupRepository.editGroup(groupId, groupName, description, imageUri)) {
                is NetworkResult.Success -> Log.d(TAG, "Succesfully edited")
                is NetworkResult.Error -> Log.d(TAG, "editGroup ${response.exception}" )
            }
        }
    }
}