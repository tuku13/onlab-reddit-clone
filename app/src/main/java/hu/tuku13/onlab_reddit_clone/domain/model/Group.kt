package hu.tuku13.onlab_reddit_clone.domain.model

data class Group(
    val createdBy: User,
    val description: String = "",
    val groupImageUrl: String = "",
    val id: Long = 0,
    val name: String = "",
    val members: Int = 0,
    val userSubscribed: Boolean
)
