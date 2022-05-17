package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.R
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton

@Composable
fun ProfileBannerImage(
    imageUrl: String,
    isMineProfile: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Log.d("imageTest", "erteke: $imageUrl")
        Log.d("imageTest", "isBlank: ${imageUrl.isBlank()}")

        if (imageUrl.isBlank()) {
            GlideImage(
                imageModel = Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.upload_image_placeholder),
                    contentDescription = "Banner image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(256.dp)
                        .fillMaxWidth()
                ),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(256.dp)
                    .fillMaxWidth()
            )
        } else {
            GlideImage(
                imageModel = imageUrl,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.heightIn(max = 256.dp),
            )
        }

        if (isMineProfile) {
            FilledButton(
                text = "Edit Image",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp),
                onClick = onClick
            )
        }
    }
}