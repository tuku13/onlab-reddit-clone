package hu.tuku13.onlab_reddit_clone.ui.screen.home

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

        LazyColumn {
            item {
                ChipGroup(
                    newOnClick = { },
                    topOnClick = { },
                    trendingOnClick = { }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(posts.value?.size ?: 0) { index ->
                PostCard(post = posts.value!![index])
            }
        }

    }
}
