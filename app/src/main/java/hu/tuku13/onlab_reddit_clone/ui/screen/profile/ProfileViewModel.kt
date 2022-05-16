package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.LikeValue
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.repository.UserRepository
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import hu.tuku13.onlab_reddit_clone.ui.screen.home.TAG
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : ViewModel() {
    private var _user: MutableLiveData<User?> = MutableLiveData()
    val user: LiveData<User?>
        get() = _user

    private var _posts: MutableLiveData<List<Post>> = MutableLiveData(emptyList())
    val posts: LiveData<List<Post>>
        get() = _posts

    var userId: Long = 0L

    fun refresh() {
        getUser()
        getPosts()
    }

    private fun getUser() {
        viewModelScope.launch {
            val user = userRepository.getUser(userId)
            _user.postValue(user)
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            when (val result = postRepository.getUserPosts(userId)) {
                is NetworkResult.Success -> _posts.postValue(
                    result.value.sortedByDescending { it.timestamp }
                )
                is NetworkResult.Error -> println(result.exception)
            }
        }
    }

    fun likePost(post: Post, likeValue: LikeValue) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = postRepository.likePost(post.postId, likeValue)) {
                is NetworkResult.Success -> getPosts()
                is NetworkResult.Error -> Log.d(TAG, result.exception.toString())
            }
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.deletePost(postId)) {
                is NetworkResult.Success -> getPosts()
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }
}