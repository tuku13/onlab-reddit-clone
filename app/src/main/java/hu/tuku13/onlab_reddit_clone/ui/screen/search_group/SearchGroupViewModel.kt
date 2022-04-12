package hu.tuku13.onlab_reddit_clone.ui.screen.search_group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.network.model.GroupDTO
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private var _groups: MutableLiveData<List<GroupDTO>> = MutableLiveData(listOf())
    val groups: LiveData<List<GroupDTO>>
        get() = _groups

    init {
        search("")
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val groups = groupRepository.searchGroups(query)
            _groups.postValue(groups)
        }
    }
}