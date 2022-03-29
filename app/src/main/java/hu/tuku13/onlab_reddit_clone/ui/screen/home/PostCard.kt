package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.network.model.Post
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(post: Post) {
    Card(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding(vertical = 8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier
            .width(360.dp)
            .wrapContentHeight()
        ) {
            PostTitleBar(post = post)

            GlideImage(
                imageModel = "https://picsum.photos/536/354",
                modifier = Modifier.width(360.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.text,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32",
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

@Composable
fun PostTitleBar(post: Post) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.width(360.dp).height(72.dp)
    ){
        // TODO Ez Group logo
        GlideImage(
            imageModel = "https://picsum.photos/40",
            modifier = Modifier.padding(16.dp).clip(CircleShape).size(40.dp)
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = "Group name",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "username",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "35m",
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

@Composable
fun PostActionBar(post: Post) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .width(360.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LikeBar(
            likes = 1100,
            userOpinion = 1,
            like = {
                // TODO ViewModel like fgv
            },
            dislike = {
                //TODO ViewModel dislike fgv
            }
        )

        Comment(
            comments = 1100,
            userAlreadyCommented = false,
            onClick = {
                // TODO comment gombra kattintas lekezelese
            }
        )

        FilledButton(
            text = "Read More",
            onClick = {
                // TODO atvinni a post details screen-re
            }
        )
    }
}

@Composable
fun LikeBar(
    likes: Int,
    userOpinion: Int,
    like: () -> Unit,
    dislike: () -> Unit,
) {
    Row() {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Like",
            modifier = Modifier.clickable { like() },
            tint = if(userOpinion == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "$likes",
            color = if(userOpinion == 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = "Dislike",
            modifier = Modifier.clickable { dislike() },
            tint = if(userOpinion == -1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Comment(
    comments: Int,
    userAlreadyCommented: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.ChatBubbleOutline,
            contentDescription = "Comments",
            tint = if (userAlreadyCommented) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = "$comments",
            color = if(userAlreadyCommented) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
