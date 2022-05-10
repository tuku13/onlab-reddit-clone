package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.screen.home.PostCard

@Composable
fun ProfileScreen(
    userId: Long,
    navigationService: NavigationService,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user.observeAsState()
    val posts = viewModel.posts.observeAsState(emptyList())

    LaunchedEffect(viewModel) {
        viewModel.userId = userId
        viewModel.refresh()
    }

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            user.value?.let {
                ProfileBannerImage(
                    imageUrl = it.profileImage,
                    onClick = { }
                )
            }
        }

        item {
            user.value?.let {
                InfoCard(it)
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(posts.value) { post ->
            PostCard(
                post = post,
                navigationService = navigationService,
                onLike = { likeValue ->
                    viewModel.likePost(post, likeValue)
                },
                onDelete = {
                    viewModel.deletePost(post.postId)
                }
            )
        }
    }
}

