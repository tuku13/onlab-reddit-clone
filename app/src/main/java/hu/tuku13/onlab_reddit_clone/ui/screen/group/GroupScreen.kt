package hu.tuku13.onlab_reddit_clone.ui.screen.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import hu.tuku13.onlab_reddit_clone.domain.model.PostSorting
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.components.ChipGroup
import hu.tuku13.onlab_reddit_clone.ui.screen.home.PostCard

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

