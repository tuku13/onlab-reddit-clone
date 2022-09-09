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

    private var _isMineProfile: MutableLiveData<Boolean> = MutableLiveData(false)
    val isMineProfile: LiveData<Boolean>
        get() = _isMineProfile

    var userId: Long = 0L

    fun refresh() {
        getUser()
        getPosts()
    }

    private fun getUser() {
        viewModelScope.launch {
            when (val response = userRepository.getUser(userId)) {
                is NetworkResult.Success -> {
                    _user.postValue(response.value)
                    _isMineProfile.postValue(response.value.id == authenticationService.userId.value)
                }
                is NetworkResult.Error -> Log.d("ProfileVM", "${response.exception}")
            }
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

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationService.logout()
        }
    }
}