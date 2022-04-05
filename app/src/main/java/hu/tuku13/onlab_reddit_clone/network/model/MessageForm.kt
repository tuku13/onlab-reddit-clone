package hu.tuku13.onlab_reddit_clone.network.model

data class MessageForm(
    val senderId: Long,
    val recipientName: String,
    val text: String
)