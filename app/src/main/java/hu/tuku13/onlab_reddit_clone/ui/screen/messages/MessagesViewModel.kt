package hu.tuku13.onlab_reddit_clone.ui.screen.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.model.Contact

import hu.tuku13.onlab_reddit_clone.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private var _contacts: MutableLiveData<List<Contact>> = MutableLiveData(emptyList())
    val contacts: LiveData<List<Contact>>
        get() = _contacts

    fun refreshContacts() {
        val userId = authenticationService.userId
        userId.value?.let { getContacts(it) }
    }

    private fun getContacts(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = messageRepository.getContacts(userId)
            _contacts.postValue(contacts)
        }
    }

    init {
        refreshContacts()
    }

//    init {
//
////        _contacts.postValue(
////            listOf(
////                Contact(
////                    userId = 0L,
////                    name = "Pityu",
////                    profileImageUrl = "",
////                    lastMessage = "asddddddddddddddddddddddddddddddddddddddsdsfsdfd",
////                    timestamp = 4352312313L
////                ),
////                Contact(
////                    userId = 0L,
////                    name = "asdsadasd",
////                    profileImageUrl = "",
////                    lastMessage = "asddddddddddddddddddddddddddddddddddddddsdsfsdfd",
////                    timestamp = 12423412313L
////                ),
////                Contact(
////                    userId = 1L,
////                    name = "Pitsdfsdfsdyu",
////                    profileImageUrl = "",
////                    lastMessage = "ccccccccccccccccccccccccccccccccccccccccccccccccc",
////                    timestamp = 7547365412313L
////                ),
////                Contact(
////                    userId = 2L,
////                    name = "sdfu",
////                    profileImageUrl = "",
////                    lastMessage = "llllllllllllllllllllllllllllllllllllll",
////                    timestamp = 12312312313L
////                )
////            )
////        )
//    }
}