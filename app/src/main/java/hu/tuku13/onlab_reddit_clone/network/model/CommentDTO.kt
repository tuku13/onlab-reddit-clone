package hu.tuku13.onlab_reddit_clone.network.model

import hu.tuku13.onlab_reddit_clone.domain.model.Comment
import hu.tuku13.onlab_reddit_clone.domain.model.User

data class CommentDTO(
    val id: Long,
    val timestamp: Long,
    val text: String,
    val parentCommentId: Long?,
    val postId: Long,
    val postedBy: Long
) {
    fun toComment(user: User) = Comment(
        id = id,
        timestamp = timestamp,
        text = text,
        parentCommentId = parentCommentId,
        postId = postId,
        user = user,
    )
}
