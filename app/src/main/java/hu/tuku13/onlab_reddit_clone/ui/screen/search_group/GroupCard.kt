package hu.tuku13.onlab_reddit_clone.ui.screen.search_group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.R
import hu.tuku13.onlab_reddit_clone.network.model.GroupDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(
    group: GroupDTO,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(360.dp)
            .wrapContentHeight(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        containerColor = MaterialTheme.colorScheme.surface,
        onClick = onClick
    ) {

        GroupTitleBar(group = group)

        Box(
            modifier = Modifier
                .width(358.dp)
                .height(200.dp)
        ) {
            GlideImage(
                imageModel = group.groupImageUrl,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = group.description,
            maxLines = 3,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}