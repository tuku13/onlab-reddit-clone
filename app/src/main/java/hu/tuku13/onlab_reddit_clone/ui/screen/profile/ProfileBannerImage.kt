package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton

@Composable
fun ProfileBannerImage(
    imageUrl: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        GlideImage(
            imageModel = imageUrl,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.heightIn(max = 256.dp),
        )

        FilledButton(
            text = "Edit Image",
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 8.dp, end = 8.dp)
        ) {
            onClick()
        }
    }
}