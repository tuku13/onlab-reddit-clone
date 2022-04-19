package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.model.PostSorting
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.ui.screen.group.sorted
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "Home VM"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private var _posts: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _postSorting: MutableLiveData<PostSorting> = MutableLiveData(PostSorting.NEW)
    val postSorting: LiveData<PostSorting>
        get() = _postSorting

    private var _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    init {
        getPosts()
    }

    fun sort(sorting: PostSorting) {
        _postSorting.value = sorting
        _posts.value?.also {
            _posts.value = it.sorted(sorting)
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = postRepository.getSubscribedPosts(authenticationService.userId.value ?: 0L)) {
                is NetworkResult.Success -> {
                    delay(1000L) // TODO kivenni végleges alkalmazásból

                    _posts.postValue(result.value.sorted(postSorting.value))


                    _isRefreshing.postValue(false)
                }
                is NetworkResult.Error -> {
                    println(result.exception)
                }
            }
        }
    }
}