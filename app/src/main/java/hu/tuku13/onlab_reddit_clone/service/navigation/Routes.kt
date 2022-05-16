package hu.tuku13.onlab_reddit_clone.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Route(
    val title: String,
    val route: String,
    val icon: ImageVector = Icons.Outlined.Home,
    val navigation: String = route
) {
    object HomeRoute : Route(
        title = "Home",
        route = "home_screen",
        icon = Icons.Outlined.Home,
    )

    object CreateGroupRoute : Route(
        title = "Create Group",
        route = "create_group_screen",
        icon = Icons.Default.Add,
    )

    object MessagesRoute :
        Route(
            title = "Messages",
            route = "messages_screen",
            icon = Icons.Default.ChatBubbleOutline,
        )

    object ProfileRoute :
        Route(
            title = "Profile",
            route = "profile_screen",
            icon = Icons.Default.PersonOutline,
        )

    class ConversationRoute(
        partnerUserId: Long,
        partnerUserName: String,
        partnerProfileImageUrl: String
    ) : Route(
        title = partnerUserName,
        route = "conversation_screen/$partnerUserId/$partnerUserName/${
            URLEncoder.encode(
                partnerProfileImageUrl,
                StandardCharsets.UTF_8.toString()
            )
        }",
        navigation = "conversation_screen/{partnerUserId}/{partnerUserName}/{partnerProfileImageUrl}"
    ) {
        companion object {
            const val navigation =
                "conversation_screen/{partnerUserId}/{partnerUserName}/{partnerProfileImageUrl}"
        }
    }

    class GroupRoute(val groupId: Long, val groupName: String) : Route(
        title = groupName,
        route = "group/$groupId",
        navigation = "group/{groupId}"
    ) {
        companion object {
            const val navigation = "group/{groupId}"
        }
    }

    object SearchGroupRoute : Route(
        title = "",
        route = "search_group"
    )

    class CreatePostRoute(groupId: Long, groupName: String) : Route(
        title = groupName,
        route = "create_post/$groupId",
        navigation = navigation
    ) {
        companion object {
            const val navigation = "create_post/{groupId}"
        }
    }

    class PostRoute(val postId: Long, groupName: String) : Route(
        title = groupName,
        route = "post/$postId",
        navigation = CreatePostRoute.navigation
    ) {
        companion object {
            const val navigation = "post/{postId}"
        }
    }

    class EditGroupRoute(val groupId: Long) : Route(
        title = "Edit Group",
        route = "edit_group/$groupId",
        navigation = "edit_group/{groupId}"
    ) {
        companion object {
            const val navigation = "edit_group/{groupId}"
        }
    }
}