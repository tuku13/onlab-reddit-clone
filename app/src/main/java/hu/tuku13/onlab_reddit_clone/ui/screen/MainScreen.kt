package hu.tuku13.onlab_reddit_clone.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.tuku13.onlab_reddit_clone.ui.navigation.BottomNavItem
import hu.tuku13.onlab_reddit_clone.ui.navigation.Routes
import hu.tuku13.onlab_reddit_clone.ui.screen.create_group.CreateGroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.home.HomeScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.messages.MessagesScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.profile.ProfileScreen
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended
import androidx.compose.runtime.*
import androidx.navigation.*
import hu.tuku13.onlab_reddit_clone.ui.screen.conversation.ConversationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var title by remember { mutableStateOf("Home") }
    var canPop by remember { mutableStateOf(false) }

    navController.addOnDestinationChangedListener { controller, _, _ ->
        val backStackEntry = controller.currentBackStackEntry

        canPop = if (backStackEntry == null) {
            false
        } else {
            when (backStackEntry.destination.route) {
                Routes.HOME_SCREEN,
                Routes.CREATE_GROUP_SCREEN,
                Routes.MESSAGES_SCREEN,
                Routes.PROFILE_SCREEN -> false
                else -> true
            }
        }

    }

    Scaffold(
        topBar = {
            TopBar(
                title = title,
                canPop = canPop,
                navController = navController
            )
        },
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomNavItem(
                        "Home",
                        Routes.HOME_SCREEN,
                        Icons.Outlined.Home
                    ),
                    BottomNavItem(
                        "Create Group",
                        Routes.CREATE_GROUP_SCREEN,
                        Icons.Default.Add
                    ),
                    BottomNavItem(
                        "Messages",
                        Routes.MESSAGES_SCREEN,
                        Icons.Default.ChatBubbleOutline
                    ),
                    BottomNavItem(
                        "Profile",
                        Routes.PROFILE_SCREEN,
                        Icons.Default.PersonOutline
                    ),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                    when (it.route) {
                        Routes.HOME_SCREEN -> title = "Home"
                        Routes.PROFILE_SCREEN -> title = "Profile"
                        Routes.MESSAGES_SCREEN -> title = "Messages"
                        Routes.CREATE_GROUP_SCREEN -> title = "Create New Group"
                    }
                }
            )
        },
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME_SCREEN,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Routes.HOME_SCREEN) {
                    HomeScreen()
                }
                composable(Routes.CREATE_GROUP_SCREEN) {
                    CreateGroupScreen()
                }
                composable(Routes.MESSAGES_SCREEN) {
                    MessagesScreen(navController)
                }
                composable(Routes.PROFILE_SCREEN) {
                    ProfileScreen()
                }
                composable(
                    route = "${Routes.CONVERSATION_SCREEN}/{partnerUserId}",
                    arguments = listOf(
                        navArgument("partnerUserId") {
                            type = NavType.LongType
                        }
                    )
                ) {
                    val partnerUserId = it.arguments?.getLong("partnerUserId") ?: 0L
                    ConversationScreen(
                        partnerUserId = partnerUserId,
                        navController = navController
                    )
                }
            }
        }
    )
}

@Composable
fun TopBar(title: String, navController: NavController, canPop: Boolean) {
    if (canPop) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            elevation = 0.dp,
            backgroundColor = Extended.surface2
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            elevation = 0.dp,
            backgroundColor = Extended.surface2
        )
    }


}

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(

    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route ?: false

            NavigationBarItem(
                selected = selected,
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(

                ),
                label = {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                    )
                }
            )
        }
    }

//    BottomNavigation(
//        backgroundColor = Extended.surface2,
//        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//        elevation = 0.dp
//    ) {
//
//    }
}