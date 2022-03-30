package hu.tuku13.onlab_reddit_clone.ui.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.PostSorting
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.Post
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
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

    private var _postSorting: PostSorting = PostSorting.NEW

    private var _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    init {
        getPosts()
    }

    fun setSorting(sorting: PostSorting) {
        _postSorting = sorting
        refresh()
    }

    fun refresh() {
        _isRefreshing.value = true
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val posts = postRepository.getSubscribedPosts(authenticationService.userId.value ?: 0L)

            delay(1000L) // TODO kivenni végleges alkalmazásból

            when (_postSorting) {
                PostSorting.TOP -> _posts.postValue(posts.sortedByDescending { it.likes })
                PostSorting.TRENDING -> _posts.postValue(posts.sortedByDescending { it.likes / (System.currentTimeMillis() - it.timestamp)})
                PostSorting.NEW -> _posts.postValue(posts.sortedByDescending { it.timestamp })
            }

            _isRefreshing.postValue(false)
        }
    }
}