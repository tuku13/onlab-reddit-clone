package hu.tuku13.onlab_reddit_clone.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.navigation.Route
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import hu.tuku13.onlab_reddit_clone.ui.scaffold.*
import hu.tuku13.onlab_reddit_clone.ui.screen.conversation.ConversationScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.create_group.CreateGroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.create_post.CreatePostScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.edit_group.EditGroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.group.GroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.home.HomeScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.messages.MessagesScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.post.PostScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.profile.ProfileScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.search_group.SearchGroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.search_group.SearchGroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authenticationService: AuthenticationService,
    navigationService: NavigationService
) {
    val navController = rememberNavController()

    LaunchedEffect(navigationService) {
        navigationService.navController = navController
    }

    navController.addOnDestinationChangedListener(listener = { _, navDestination, _ ->
        navigationService.onPopBackStack(navDestination)
    })

    val route = navigationService.currentRoute.observeAsState(Route.HomeRoute)
    val searchGroupViewModel: SearchGroupViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            when (route.value) {
                is Route.GroupRoute -> {}
                is Route.EditGroupRoute,
                is Route.CreatePostRoute,
                is Route.PostRoute,
                is Route.ConversationRoute -> SubScreenTopBar(
                    title = route.value.title,
                    navigationService = navigationService
                )
                is Route.SearchGroupRoute -> SearchGroupTopBar(
                    navigationService = navigationService,
                    viewModel = searchGroupViewModel
                )
                is Route.HomeRoute -> HomeScreenTopBar(navigationService = navigationService)
                else -> BaseScreenTopBar(title = route.value.title)
            }
        },
        bottomBar = {
            when (route.value) {
                is Route.ConversationRoute,
                is Route.SearchGroupRoute,
                is Route.PostRoute -> {}
                else -> BottomBar(
                    routes = listOf(
                        Route.HomeRoute,
                        Route.CreateGroupRoute,
                        Route.MessagesRoute,
                        Route.ProfileRoute
                    ),
                    navigationService = navigationService,
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            when (route.value) {
                is Route.GroupRoute -> {
                    LargeFloatingActionButton(
                        onClick = {
                            navigationService.navigate(
                                Route.CreatePostRoute(
                                    groupId = (route.value as Route.GroupRoute).groupId,
                                    groupName = (route.value as Route.GroupRoute).groupName
                                )
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Create Post",
                            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                        )
                    }
                }
                else -> {}
            }
        },
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Route.HomeRoute.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Route.HomeRoute.navigation) {
                    HomeScreen(navigationService)
                }
                composable(Route.CreateGroupRoute.navigation) {
                    CreateGroupScreen(navigationService)
                }
                composable(Route.MessagesRoute.navigation) {
                    MessagesScreen(navigationService)
                }
                composable(Route.ProfileRoute.navigation) {
                    ProfileScreen(
                        userId = authenticationService.userId.value ?: 0L,
                        navigationService = navigationService
                    )
                }
                composable(
                    route = Route.ConversationRoute.navigation,
                    arguments = listOf(
                        navArgument("partnerUserId") {
                            type = NavType.LongType
                        },
                        navArgument("partnerUserName") {
                            type = NavType.StringType
                        },
                        navArgument("partnerProfileImageUrl") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val partnerUserId = it.arguments?.getLong("partnerUserId") ?: 0L
                    val partnerUserName = it.arguments?.getString("partnerUserName") ?: ""
                    val partnerProfileImageUrl =
                        it.arguments?.getString("partnerProfileImageUrl") ?: ""

                    ConversationScreen(partnerUserId, partnerUserName, partnerProfileImageUrl)
                }
                composable(Route.SearchGroupRoute.navigation) {
                    SearchGroupScreen(
                        navigationService = navigationService,
                        viewModel = searchGroupViewModel
                    )
                }
                composable(
                    route = Route.GroupRoute.navigation,
                    arguments = listOf(
                        navArgument("groupId") {
                            type = NavType.LongType
                        }
                    )
                ) {
                    val groupId = it.arguments?.getLong("groupId") ?: 0L
                    GroupScreen(
                        groupId = groupId,
                        navigationService = navigationService
                    )
                }
                composable(
                    route = Route.CreatePostRoute.navigation,
                    arguments = listOf(
                        navArgument("groupId") {
                            type = NavType.LongType
                        }
                    )
                ) {
                    val groupId = it.arguments?.getLong("groupId") ?: 0L
                    CreatePostScreen(
                        groupId = groupId,
                        navigationService = navigationService
                    )
                }
                composable(
                    route = Route.PostRoute.navigation,
                    arguments = listOf(
                        navArgument("postId") {
                            type = NavType.LongType
                        }
                    )
                ) {
                    val postId = it.arguments?.getLong("postId") ?: 0L
                    PostScreen(
                        postId = postId,
                        navigationService = navigationService
                    )
                }
                composable(
                    route = Route.EditGroupRoute.navigation,
                    arguments = listOf(
                        navArgument("groupId"){
                            type = NavType.LongType
                        }
                    )
                ) {
                    val groupId = it.arguments?.getLong("groupId") ?: 0L
                    EditGroupScreen(
                        groupId = groupId,
                        navigationService = navigationService
                    )
                }
            }
        }
    )
}

