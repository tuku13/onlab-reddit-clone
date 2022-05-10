package hu.tuku13.onlab_reddit_clone.ui.screen.edit_group

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.components.ImageUploader
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton

@Composable
fun EditGroupScreen(
    groupId: Long,
    navigationService: NavigationService,
    viewModel: EditGroupViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val group = viewModel.group.observeAsState()
    var groupName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.loadGroup(groupId)
    }

    DisposableEffect(lifecycleOwner) {
        val observer: Observer<Group?> = Observer {
            if(it != null) {
                groupName = it.name
                description = it.description
            }
        }

        viewModel.group.observe(lifecycleOwner, observer)

        onDispose {
            viewModel.group.removeObserver(observer)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

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
            defaultImageUrl = group.value?.groupImageUrl,
            onUpload = { imageUri = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            OutlinedButton(
                text = "Cancel",
                onClick = {
                    groupName = ""
                    description = ""
                    imageUri = null
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            TonalButton(
                text = "Save",
                onClick = {
                    viewModel.editGroup(
                        groupId = groupId,
                        groupName = groupName,
                        description = description,
                        imageUri = imageUri
                    )
                }
            )
        }
    }

}