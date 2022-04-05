package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.GetMessageForm
import hu.tuku13.onlab_reddit_clone.network.model.Message
import hu.tuku13.onlab_reddit_clone.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private val _messages: MutableLiveData<List<Message>> = MutableLiveData(emptyList())
    val messages: LiveData<List<Message>>
        get() = _messages

    private var _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    fun refreshMessages(partnerId: Long) {
        _messages.value = emptyList()
        _isRefreshing.value = true
        getMessages(partnerId)
    }

    fun getMessages(partnerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val messages = messageRepository.getMessages(
                GetMessageForm(
                    from = authenticationService.userId.value ?: 0L,
                    to = partnerId
                )
            )

            delay(1500)

            _messages.postValue(messages)
            _isRefreshing.postValue(false)
        }
    }
}