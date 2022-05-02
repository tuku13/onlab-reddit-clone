package hu.tuku13.onlab_reddit_clone.network.model

import hu.tuku13.onlab_reddit_clone.domain.model.Like
import hu.tuku13.onlab_reddit_clone.domain.model.LikeValue

data class LikeDTO(
    val id: Long,
    val value: Int,
    val userId: Long,
    val postId: Long?,
    val commentId: Long?
) {
    fun toLike() = Like(
        id = id,
        value = when {
            value < 0 -> LikeValue.Dislike
            value > 0 -> LikeValue.Like
            else -> LikeValue.None
        },
        userId = userId,
        postId = postId,
        commentId = commentId
    )
}
