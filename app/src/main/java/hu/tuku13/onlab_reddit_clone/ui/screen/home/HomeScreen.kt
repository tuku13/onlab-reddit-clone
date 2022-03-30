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
import hu.tuku13.onlab_reddit_clone.ui.components.ChipGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        val posts = viewModel.posts.observeAsState()
        val isRefreshing = viewModel.isRefreshing.observeAsState()

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value!!),
            onRefresh = { viewModel.refresh() }
        ) {
            LazyColumn {
                item {
                    ChipGroup(
                        newOnClick = { viewModel.setSorting(PostSorting.NEW) },
                        topOnClick = { viewModel.setSorting(PostSorting.TOP) },
                        trendingOnClick = { viewModel.setSorting(PostSorting.TRENDING) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(posts.value?.size ?: 0) { index ->
                    PostCard(post = posts.value!![index])
                }
            }
        }

    }
}
