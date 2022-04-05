package hu.tuku13.onlab_reddit_clone.repository

import hu.tuku13.onlab_reddit_clone.network.model.Contact
import hu.tuku13.onlab_reddit_clone.network.model.GetMessageForm
import hu.tuku13.onlab_reddit_clone.network.model.Message
import hu.tuku13.onlab_reddit_clone.network.model.MessageForm
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val api: RedditCloneApi
) {
    val TAG = "Message Repo"

    suspend fun getContacts(userId: Long): List<Contact> {
        val response = api.getContacts(userId)

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            emptyList()
        }
    }

    suspend fun getMessages(form: GetMessageForm): List<Message> {
        val response = api.getMessages(form)

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            emptyList()
        }
    }

    suspend fun sendMessage(form: MessageForm): Boolean {
        val response = api.sendMessage(form)

        return response.isSuccessful
    }
}