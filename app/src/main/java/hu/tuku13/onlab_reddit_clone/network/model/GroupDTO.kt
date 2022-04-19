package hu.tuku13.onlab_reddit_clone.network.model

import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.model.User

data class GroupDTO(
    val createdBy: Long = 0,
    val description: String = "",
    val groupImageUrl: String = "",
    val id: Long = 0,
    val name: String = "",
    val members: Int = 0
) {
    fun toGroup(user: User): Group = Group(
        createdBy = user,
        description = description,
        groupImageUrl = groupImageUrl,
        id = id,
        name = name,
        members = members
    )
}