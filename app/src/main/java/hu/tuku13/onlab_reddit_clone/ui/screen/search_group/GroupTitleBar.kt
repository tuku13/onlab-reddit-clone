package hu.tuku13.onlab_reddit_clone.ui.screen.search_group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.network.model.GroupDTO


@Composable
fun GroupTitleBar(group: GroupDTO) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(360.dp)
            .height(72.dp)
    ) {
        GlideImage(
            imageModel = if (group.groupImageUrl != "") group.groupImageUrl else "https://picsum.photos/40",
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .size(40.dp)
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = group.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = if(group.members == 0) "No members yet" else "${group.members} members",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

        }

//        Icon(
//            imageVector = Icons.Default.MoreVert,
//            contentDescription = "Options",
//            tint = MaterialTheme.colorScheme.onSurfaceVariant,
//            modifier = Modifier.padding(16.dp)
//        )
    }
}