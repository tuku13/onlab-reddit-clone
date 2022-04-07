package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.tuku13.onlab_reddit_clone.ui.screen.home.PostCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    userId: Long
) {
    val user = viewModel.user.observeAsState()
    val posts = viewModel.posts.observeAsState()

    LaunchedEffect(key1 = Any()) {
        viewModel.refresh(userId)
    }

    posts.value?.let {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                ProfileBannerImage(
                    imageUrl = "https://hatrabbits.com/wp-content/uploads/2017/01/random.jpg",
                    onClick = {}
                )
            }

            item {
                user.value?.let { InfoCard(it) }
            }

            items(it.size) { index ->
                PostCard(post = it[index])
            }
        }
    }
}

