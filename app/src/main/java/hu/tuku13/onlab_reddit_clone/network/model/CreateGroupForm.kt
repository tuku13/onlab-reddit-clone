package hu.tuku13.onlab_reddit_clone.network.model

data class CreateGroupForm(
    val userId: Long,
    val groupName: String,
    val description: String,
    val imageUrl: String = ""
)
