package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.PostDTO
import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.repository.UserRepository
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
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

    fun refresh(userId: Long) {
        getUser(userId)
        getPosts(userId)
    }

    private fun getUser(userId: Long) {
        viewModelScope.launch {
            val user = userRepository.getUser(userId)
            _user.postValue(user)
        }
    }

    private fun getPosts(userId: Long) {
        viewModelScope.launch {
            when (val result = postRepository.getUserPosts(userId)) {
                is NetworkResult.Success -> _posts.postValue(
                    result.value.sortedByDescending { it.timestamp }
                )
                is NetworkResult.Error -> println(result.exception)
            }
        }
    }
}