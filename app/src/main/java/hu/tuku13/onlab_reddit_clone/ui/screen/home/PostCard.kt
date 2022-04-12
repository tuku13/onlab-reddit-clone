package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.network.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(post: Post) {
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
            PostTitleBar(post = post)

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

fun formatElapsedTime(time: Long): String {
    val now = System.currentTimeMillis()
    val elapsed = now - time

    val elapsedInMinutes = elapsed / (1000 * 60)

    return if (elapsedInMinutes <= 60) {
        "${elapsedInMinutes}m"
    } else if (elapsedInMinutes > 60 || elapsedInMinutes <= 24 * 60) {
        "${elapsedInMinutes / 60}h"
    } else {
        "${elapsedInMinutes / (60 * 24)}d"
    }
}

