package hu.tuku13.onlab_reddit_clone.network.model

import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.model.User

data class PostDTO(
    val postId: Long,
    val comments: Int,
    val likes: Int,
    val userOpinion: Int = 0,
    val userCommented: Boolean = false,
    val groupImage: String,
    val groupName: String,
    val title: String,
    val text: String,
    val postImage: String,
    val timestamp: Long,
    val groupId: Long,
    val userId: Long
) {
    fun toPost(user: User, isOwnPost: Boolean) = Post(
        postId = postId,
        comments = comments,
        likes = likes,
        userOpinion = userOpinion,
        userCommented = userCommented,
        groupImage = groupImage,
        groupName = groupName,
        title = title,
        text = text,
        postImage = postImage,
        timestamp = timestamp,
        groupId = groupId,
        user = user,
        isOwnPost = isOwnPost
    )
}

