package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
                    tint = if(text == "") MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary,
                )
            }
        }
    )
}