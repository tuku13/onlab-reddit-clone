package hu.tuku13.onlab_reddit_clone.network.model

data class CommentFormDTO(
    val userId: Long,
    var text : String,
    val parentCommentId : Long?
)
