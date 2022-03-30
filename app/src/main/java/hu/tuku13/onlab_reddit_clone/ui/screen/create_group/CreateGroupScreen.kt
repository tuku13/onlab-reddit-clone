package hu.tuku13.onlab_reddit_clone.ui.screen.create_group

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton

@Composable
fun CreateGroupScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        var groupName by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var imageUrl by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = groupName,
            label = "Group name",
            onValueChange = { groupName = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = description,
            label = "Description",
            onValueChange = { description = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        ImageUploader(
            onUpload = { imageUrl = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            OutlinedButton(
                text = "Cancel",
                onClick = {}
            )

            Spacer(modifier = Modifier.width(8.dp))

            TonalButton(
                text = "Create",
                onClick = {}
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageUploader(
    onUpload: (String) -> Unit
) {
    Card(
        modifier = Modifier.size(360.dp),
        containerColor = Color.Red
    ) {

    }
}
