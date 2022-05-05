package hu.tuku13.onlab_reddit_clone.ui.screen.create_group

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.components.*
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Composable
fun CreateGroupScreen(
    navigationService: NavigationService,
    viewModel: CreateGroupViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var groupName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        DisposableEffect(lifecycleOwner) {
            val observer: Observer<Group?> = Observer {
                if(it != null) {
                    navigationService.navigate(Route.GroupRoute(
                        groupId = it.id,
                        groupName = it.name
                    ))
                }
            }

            viewModel.groupCreated.observe(lifecycleOwner, observer)

            onDispose {
                viewModel.groupCreated.removeObserver(observer)
            }
        }

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
                text = "Create",
                onClick = {
                    viewModel.createGroup(groupName, description, imageUri)
                }
            )
        }

    }
}

fun getPathFromUri(uri: Uri, context: Context) {
    val fileDescriptor = context.contentResolver.openFileDescriptor(uri, "")
}