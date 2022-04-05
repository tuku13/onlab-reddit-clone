package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.twotone.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.tuku13.onlab_reddit_clone.network.model.Message
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun ConversationScreen(
    partnerUserId: Long,
    navController: NavController,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    val messages = viewModel.messages.observeAsState()

    LaunchedEffect(key1 = Any()) {
        viewModel.refreshMessages(partnerUserId)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            items(messages.value?.size ?: 0) { index ->
                ChatBubble(messages.value!![index])
            }
        }

        MessageInputBar()
    }
}

@Composable
fun ChatBubble(message: Message) {
    Text(
        text = message.text,
        modifier = Modifier.padding(top = 16.dp)
    )
}

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

@Composable
fun ChatTextField() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = RoundedCornerShape(90.0f),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            backgroundColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        placeholder = {
            Text(
                text = "Message",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        trailingIcon = {
            IconButton(onClick = {
                text = ""
                // TODO send message
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send message",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    )
}


