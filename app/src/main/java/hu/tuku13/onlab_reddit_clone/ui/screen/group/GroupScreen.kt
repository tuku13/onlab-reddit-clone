package hu.tuku13.onlab_reddit_clone.ui.screen.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

    LaunchedEffect(viewModel) { viewModel.refresh(groupId) }

    LaunchedEffect(sorting.value) { viewModel.refresh(groupId) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value!!),
        onRefresh = { viewModel.refresh(groupId) }
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

            items(posts.value) { post ->
                PostCard(
                    post = post,
                    navigationService = navigationService
                )
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("GroupId = $groupId")
    }
}