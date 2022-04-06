package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.network.model.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBubble(
    message: Message,
    partnerUserId: Long,
    partnerProfileImage: String
) {
    val isReceived = message.senderId == partnerUserId

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = if (isReceived) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (isReceived) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                GlideImage(
                    imageModel = if (partnerProfileImage == "") "https://picsum.photos/40" else partnerProfileImage,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clip(CircleShape)
                        .size(40.dp)
                )
            }

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(70.0f)
            ) {
                TextBubble(
                    text = message.text,
                    isReceived = isReceived
                )
            }

            Box(
                modifier = Modifier
                    .height(20.dp)
                    .weight(20.0f)
            )

        } else {

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(20.0f)
                    .weight(20.0f)
                    .background(Color.Red)
            )

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(80.0f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth(80.0f)
                ) {
                    TextBubble(
                        text = message.text,
                        isReceived = isReceived
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextBubble(
    text: String,
    isReceived: Boolean
) {
    Card(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentHeight()
            .wrapContentWidth(),
        shape = RoundedCornerShape(45.0f),
        containerColor = if (isReceived) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = if (isReceived) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .padding(8.dp)
                .wrapContentWidth()
                .padding(end = 16.dp)
        )
    }

}