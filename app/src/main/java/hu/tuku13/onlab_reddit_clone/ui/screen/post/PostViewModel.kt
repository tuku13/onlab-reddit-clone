package hu.tuku13.onlab_reddit_clone.ui.screen.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.model.Comment
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    private val TAG = "PostVM"
    var postId: Long = 0L

    private val _post: MutableLiveData<Post?> = MutableLiveData(null)
    val post: LiveData<Post?>
        get() = _post

    private val _comments: MutableLiveData<List<Comment>> = MutableLiveData(emptyList())
    val comments: LiveData<List<Comment>>
        get() = _comments

    val selectedComment: MutableLiveData<Comment?> = MutableLiveData(null)

    fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.getPost(postId)) {
                is NetworkResult.Success -> _post.postValue(response.value)
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }

    fun selectParent(comment: Comment?) {
        Log.d(TAG, "selectParent: ${comment?.id}")
        if (comment?.parentCommentId == null) {
            selectedComment.value = null
            getComments()
            return
        }

        selectedComment.value = comment
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.getChildrenComments(comment.parentCommentId)) {
                is NetworkResult.Success -> _comments.postValue(response.value!!)
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }

    fun selectComment(comment: Comment?) {
        if (comment == null) {
            selectedComment.postValue(null)
            getComments()
        } else {
            selectedComment.postValue(comment)
            viewModelScope.launch(Dispatchers.IO) {
                when (val response = postRepository.getChildrenComments(comment.id)) {
                    is NetworkResult.Success -> {
                        val comments = mutableListOf<Comment>().also {
                            it.add(comment)
                            it.addAll(response.value)
                        }
                        _comments.postValue(comments)
                    }
                    is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
                }
            }
        }
    }

    fun getComments() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.getCommentsAtPost(postId)) {
                is NetworkResult.Success -> {
                    val postComments = response.value!!.filter { it.parentCommentId == null }
                    _comments.postValue(postComments)
                }
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }

    fun createComment(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = postRepository.createComment(
                text = text,
                postId = postId,
                parentCommentId = selectedComment.value?.id
            )) {
                is NetworkResult.Success -> {
                    selectComment(selectedComment.value)
                    getPost()
                }
                is NetworkResult.Error -> Log.d(TAG, response.exception.toString())
            }
        }
    }

}