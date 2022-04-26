package hu.tuku13.onlab_reddit_clone.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.domain.model.User

@Composable
fun ProfileImage(
    user: User,
    size: Dp = 40.dp,
) {
    GlideImage(
        imageModel = if (user.profileImage == "") "https://picsum.photos/40" else user.profileImage,
        modifier = Modifier
            .clip(CircleShape)
            .size(size)
    )
}