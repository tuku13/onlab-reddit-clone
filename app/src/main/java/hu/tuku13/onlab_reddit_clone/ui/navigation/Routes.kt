package hu.tuku13.onlab_reddit_clone.ui.navigation

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

    class GroupRoute(groupName: String) : Route(
        title = groupName,
        route = "home_screen"
    )

    object SearchGroupRoute : Route(
        title = "",
        route = "search_group"
    )
}