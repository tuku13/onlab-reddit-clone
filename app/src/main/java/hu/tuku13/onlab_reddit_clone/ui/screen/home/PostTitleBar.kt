package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route
import hu.tuku13.onlab_reddit_clone.util.formatElapsedTime

@Composable
fun PostTitleBar(
    post: Post,
    navigationService: NavigationService
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(360.dp)
            .height(72.dp)
    ) {
        GlideImage(
            imageModel = if (post.groupImage != "") post.groupImage else "https://picsum.photos/40",
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .size(40.dp)
                .clickable {
                    navigationService.navigate(Route.GroupRoute(
                        groupId = post.groupId,
                        groupName = post.groupName
                    ))
                }
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = post.groupName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    navigationService.navigate(Route.GroupRoute(
                        groupId = post.groupId,
                        groupName = post.groupName
                    ))
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = post.user.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = formatElapsedTime(post.timestamp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Options",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(16.dp)
        )
    }
}
