package hu.tuku13.onlab_reddit_clone.ui.screen.create_post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    private val TAG = "CreatePostVM"

    private val _postCreated: MutableLiveData<Boolean> = MutableLiveData(false)
    val postCreated: LiveData<Boolean>
        get() = _postCreated

    fun createPost(groupId: Long, title: String, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = postRepository.createPost(groupId, title, text)) {
                is NetworkResult.Success ->  {
                    _postCreated.postValue(true)
                }
                is NetworkResult.Error -> {
                    _postCreated.postValue(false)
                    Log.d(TAG, "${result.exception}")
                }
            }
        }
    }
}