package hu.tuku13.onlab_reddit_clone.domain.model

data class Comment(
    val id: Long,
    val timestamp: Long,
    val text: String,
    val parentCommentId: Long?,
    val postId: Long,
    val user: User
)
