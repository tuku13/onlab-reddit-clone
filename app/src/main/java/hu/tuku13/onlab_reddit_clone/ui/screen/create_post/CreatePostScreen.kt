package hu.tuku13.onlab_reddit_clone.ui.screen.create_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreatePostScreen(
    groupId: Long,
    navigationService: NavigationService,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    DisposableEffect(lifecycleOwner) {
        val observer: Observer<Boolean> = Observer {
            if(it == true) {
                navigationService.popBackStack()
            }
        }

        viewModel.postCreated.observe(lifecycleOwner, observer)

        onDispose {
            viewModel.postCreated.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Create Post",
                modifier = Modifier.align(Alignment.Start),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = title,
                onValueChange = { title = it },
                label = "Title",
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                singleLine = false,
                maxLines = 4,
                label = "Text",
                imeAction = ImeAction.Send,
                keyboardActions = KeyboardActions(
                    onPrevious = { focusManager.moveFocus(FocusDirection.Up) },
                    onDone = {
                        keyboardController?.hide()
                        viewModel.createPost(groupId, title, text)
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(text = "Cancel") {
                navigationService.popBackStack()
            }

            Spacer(modifier = Modifier.width(8.dp))

            TonalButton(text = "Create Post") {
                keyboardController?.hide()
                viewModel.createPost(groupId, title, text)
            }
        }
    }
}