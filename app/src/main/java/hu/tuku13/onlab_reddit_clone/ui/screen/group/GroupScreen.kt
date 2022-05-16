package hu.tuku13.onlab_reddit_clone.ui.screen.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import hu.tuku13.onlab_reddit_clone.domain.model.PostSorting
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.components.ChipGroup
import hu.tuku13.onlab_reddit_clone.ui.screen.home.PostCard
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun GroupScreen(
    groupId: Long,
    navigationService: NavigationService,
    viewModel: GroupViewModel = hiltViewModel()
) {
    val group = viewModel.group.observeAsState()
    val posts = viewModel.posts.observeAsState(emptyList())
    val sorting = viewModel.postSorting.observeAsState(PostSorting.NEW)

    val isRefreshing = viewModel.isRefreshing.observeAsState()
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.groupId = groupId
        viewModel.refresh()
    }
    LaunchedEffect(sorting.value) { viewModel.refresh() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = group.value?.name ?: "",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigationService.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            elevation = 0.dp,
            backgroundColor = Extended.surface2,
            actions = {
                Box {
                    IconButton(onClick = { isMenuOpen = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(
                        expanded = isMenuOpen,
                        onDismissRequest = { isMenuOpen = false },
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Edit",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            onClick = {
                                group.value?.let {
                                    navigationService.navigate(
                                        Route.EditGroupRoute(it.id)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value!!),
            onRefresh = { viewModel.refresh() }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    group.value?.let {
                        GroupInfoCard(
                            group = it,
                            onSubscribeClick = {
                                viewModel.subscribe()
                            }
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp)
                    ) {
                        ChipGroup(
                            sorting = sorting.value,
                            newOnClick = { viewModel.sort(PostSorting.NEW) },
                            topOnClick = { viewModel.sort(PostSorting.TOP) },
                            trendingOnClick = { viewModel.sort(PostSorting.TRENDING) }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(posts.value) { post ->
                    PostCard(
                        post = post,
                        navigationService = navigationService,
                        onLike = {
                            viewModel.likePost(
                                post = post,
                                likeValue = it,
                                groupId = groupId
                            )
                        },
                        onDelete = {
                            viewModel.deletePost(post.postId)
                        }
                    )
                }

            }
        }
    }
}

