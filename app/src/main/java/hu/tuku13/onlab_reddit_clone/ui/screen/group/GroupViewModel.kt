package hu.tuku13.onlab_reddit_clone.ui.screen.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.model.LikeValue
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.model.PostSorting
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG = "GroupViewModel"

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val postRepository: PostRepository
) : ViewModel() {
    private val _group: MutableLiveData<Group?> = MutableLiveData(null)
    val group: LiveData<Group?>
        get() = _group

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _postSorting: MutableLiveData<PostSorting> = MutableLiveData(PostSorting.NEW)
    val postSorting: LiveData<PostSorting>
        get() = _postSorting

    private var _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    var groupId = 0L

    fun sort(sorting: PostSorting) {
        _postSorting.value = sorting
        _posts.value?.also {
            _posts.value = it.sorted(sorting)
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = groupRepository.getGroup(groupId)) {
                is NetworkResult.Success -> _group.postValue(result.value)
                is NetworkResult.Error -> Log.d(TAG, "${result.exception}")
            }

            when (val result = postRepository.getPosts(groupId)) {
                is NetworkResult.Success -> _posts.postValue(result.value.sorted(postSorting.value))
                is NetworkResult.Error -> println(result.exception)
            }
        }
    }

    fun likePost(post: Post, likeValue: LikeValue, groupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.likePost(post.postId, likeValue)) {
                is NetworkResult.Success -> refresh()
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.deletePost(postId)) {
                is NetworkResult.Success -> refresh()
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }

    fun subscribe() {
        val groupId = group.value?.id ?: return

        viewModelScope.launch(Dispatchers.IO) {

            when (val result = groupRepository.subscribe(groupId)) {
                is NetworkResult.Success -> refresh()
                is NetworkResult.Error -> Log.d(TAG, result.exception.toString())
            }
        }
    }

}

fun List<Post>.sorted(sorting: PostSorting?): List<Post> {
    return when (sorting) {
        PostSorting.TOP -> this.sortedByDescending { it.likes }
        PostSorting.TRENDING -> this.sortedByDescending { it.likes / (System.currentTimeMillis() - it.timestamp) }
        PostSorting.NEW -> this.sortedByDescending { it.timestamp }
        else -> this
    }
}