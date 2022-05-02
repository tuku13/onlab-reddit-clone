package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigationService: NavigationService,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 24.dp, end = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        val posts = viewModel.posts.observeAsState()
        val isRefreshing = viewModel.isRefreshing.observeAsState()
        val sorting = viewModel.postSorting.observeAsState(PostSorting.NEW)

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value!!),
            onRefresh = { viewModel.refresh() }
        ) {
            LazyColumn{
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    ) {
                        ChipGroup(
                            sorting = sorting.value,
                            newOnClick = { viewModel.sort(PostSorting.NEW) },
                            topOnClick = { viewModel.sort(PostSorting.TOP) },
                            trendingOnClick = { viewModel.sort(PostSorting.TRENDING) }
                        )
                    }
                }

                items(posts.value?.size ?: 0) { index ->
                    PostCard(
                        post = posts.value!![index],
                        navigationService = navigationService,
                        onLike = { likeValue ->
                            viewModel.likePost(posts.value!![index], likeValue)
                        }
                    )
                }
            }
        }

    }
}
