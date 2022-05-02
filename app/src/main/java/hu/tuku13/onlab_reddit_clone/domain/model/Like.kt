package hu.tuku13.onlab_reddit_clone.domain.model

data class Like(
    val id: Long,
    val value: LikeValue,
    val userId: Long,
    val postId: Long?,
    val commentId: Long?
)
