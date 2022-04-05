package hu.tuku13.onlab_reddit_clone.network.model

data class Message(
    val id: Long,
    val timestamp: Long,
    val text: String,
    val senderId: Long,
    val recipientId: Long
)