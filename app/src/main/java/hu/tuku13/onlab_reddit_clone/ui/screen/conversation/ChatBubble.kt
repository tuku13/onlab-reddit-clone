package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.network.model.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBubble(
    message: Message,
    partnerUserId: Long
) {
    val isReceived = message.senderId == partnerUserId

    Row(
        modifier = Modifier.wrapContentHeight().fillMaxWidth(),
        horizontalArrangement = if (isReceived) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
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
                text = message.text,
                style = MaterialTheme.typography.titleMedium,
                color = if (isReceived) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(8.dp).wrapContentWidth()
            )
        }
    }
}