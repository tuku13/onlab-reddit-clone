package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun MessageInputBar() {
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 80.dp)
            .heightIn(max = 160.dp)
            .wrapContentHeight()
            .background(Extended.surface2)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChatTextField()
    }
}