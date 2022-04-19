package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.domain.model.Post
import hu.tuku13.onlab_reddit_clone.network.model.PostDTO
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton

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
            likes = post.likes,
            userOpinion = post.userOpinion,
            like = {
                // TODO ViewModel like fgv
            },
            dislike = {
                //TODO ViewModel dislike fgv
            }
        )

        Comment(
            comments = post.comments,
            userAlreadyCommented = post.userCommented,
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