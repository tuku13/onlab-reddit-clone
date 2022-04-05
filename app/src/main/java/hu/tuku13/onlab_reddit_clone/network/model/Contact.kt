package hu.tuku13.onlab_reddit_clone.network.model

data class Contact(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
    val lastMessage: String,
    val timestamp: Long
)
