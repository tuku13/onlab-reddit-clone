package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.R
import hu.tuku13.onlab_reddit_clone.domain.model.LikeValue
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    post: Post,
    onLike: (LikeValue) -> Unit,
    onDelete: (Post) -> Unit,
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
                navigationService = navigationService,
                onDelete = onDelete
            )

            if (post.postImage.isBlank()) {
                GlideImage(
                    imageModel = post.postImage,
                    modifier = Modifier.width(360.dp),
                    error = ImageBitmap.imageResource(R.drawable.upload_image_placeholder)
                )
            } else {
                GlideImage(
                    imageModel = ImageBitmap.imageResource(R.drawable.upload_image_placeholder),
                    modifier = Modifier.width(360.dp),
                )
            }

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

            PostActionBar(
                post = post,
                navigateToPost = {
                    navigationService.navigate(
                        Route.PostRoute(
                        postId = post.postId,
                        groupName = post.groupName
                    ))
                },
                onLike = onLike
            )
        }
    }
}

