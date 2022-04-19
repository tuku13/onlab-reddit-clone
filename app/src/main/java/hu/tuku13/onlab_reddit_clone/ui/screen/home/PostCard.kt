package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    post: Post,
    navigationService: NavigationService
) {
    Card(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(vertical = 8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .wrapContentHeight()
        ) {
            PostTitleBar(
                post = post,
                navigationService = navigationService
            )

            GlideImage(
                imageModel = post.postImage,
                modifier = Modifier.width(360.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.title,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.text,
                maxLines = 5,
                softWrap = true,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelLarge
            )

            PostActionBar(post = post)
        }
    }
}

