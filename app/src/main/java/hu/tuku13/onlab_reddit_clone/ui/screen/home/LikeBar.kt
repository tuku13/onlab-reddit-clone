package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            tint = if (userOpinion == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "$likes",
            color = if (userOpinion == 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = "Dislike",
            modifier = Modifier.clickable { dislike() },
            tint = if (userOpinion == -1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}