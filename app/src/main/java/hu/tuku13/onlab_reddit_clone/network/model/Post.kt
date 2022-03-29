package hu.tuku13.onlab_reddit_clone.network.model

data class Post(
    val id: Long,
    val timestamp: Long,
    val text: String,
    val groupId: Long,
    val userId: Long,
    val imageUrl: String
)
