package hu.tuku13.onlab_reddit_clone.ui.screen.post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.domain.model.Comment
import hu.tuku13.onlab_reddit_clone.ui.components.ProfileImage
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended
import hu.tuku13.onlab_reddit_clone.util.formatElapsedTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentWidget(
    comment: Comment,
    selectedComment: Comment?,
    onClick: (Comment) -> Unit
) {
    val background = if (comment == selectedComment) Extended.surface2 else Color.Transparent
    val startPadding = if(selectedComment != null && comment != selectedComment) 16.dp else 0.dp
    Card(
        containerColor = background,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(start = startPadding)
            .clickable { onClick(comment) },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfileImage(
                user = comment.user,
                size = 20.dp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = comment.user.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = formatElapsedTime(comment.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Text(
            text = comment.text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}