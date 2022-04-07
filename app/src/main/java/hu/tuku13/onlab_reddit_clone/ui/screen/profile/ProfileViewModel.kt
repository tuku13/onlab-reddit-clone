package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.Post
import hu.tuku13.onlab_reddit_clone.network.model.User
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.repository.UserRepository
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
            val posts = postRepository.getUserPosts(userId)
            _posts.postValue(posts.sortedByDescending { it.timestamp })
        }
    }
}