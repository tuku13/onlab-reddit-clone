package hu.tuku13.onlab_reddit_clone.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.tuku13.onlab_reddit_clone.ui.screen.create_group.CreateGroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.home.HomeScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.messages.MessagesScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.profile.ProfileScreen
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.scaffold.*
import hu.tuku13.onlab_reddit_clone.ui.screen.conversation.ConversationScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.group.GroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.search_group.SearchGroupScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.search_group.SearchGroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    authenticationService: AuthenticationService,
    navigationService: NavigationService
) {
    val navController = rememberNavController()

    LaunchedEffect(key1 = navigationService) {
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
                is Route.ConversationRoute -> SubScreenTopBar(
                    title = route.value.title,
                    navigationService = navigationService
                )
                is Route.GroupRoute -> SubScreenTopBar(
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
                is Route.ConversationRoute -> {}
                is Route.SearchGroupRoute -> {}
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
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Route.HomeRoute.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Route.HomeRoute.navigation) {
                    HomeScreen()
                }
                composable(Route.CreateGroupRoute.navigation) {
                    CreateGroupScreen()
                }
                composable(Route.MessagesRoute.navigation) {
                    MessagesScreen(navigationService)
                }
                composable(Route.ProfileRoute.navigation) {
                    ProfileScreen(userId = authenticationService.userId.value ?: 0L)
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
                    GroupScreen(groupId)
                }
            }
        }
    )
}

