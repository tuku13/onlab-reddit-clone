package hu.tuku13.onlab_reddit_clone.network.model

import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.model.User

data class GroupDTO(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val groupImageUrl: String = "",
    val createdBy: Long = 0,
    val members: Int = 0,
) {
    fun toGroup(user: User, userSubscribed: Boolean): Group = Group(
        id = id,
        name = name,
        description = description,
        groupImageUrl = groupImageUrl,
        createdBy = user,
        members = members,
        userSubscribed = userSubscribed
    )
}