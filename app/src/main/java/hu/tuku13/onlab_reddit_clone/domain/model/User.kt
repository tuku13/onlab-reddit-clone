package hu.tuku13.onlab_reddit_clone.domain.model

data class User(
    val id: Long,
    val name: String,
    val bio: String,
    val profileImage: String
)
