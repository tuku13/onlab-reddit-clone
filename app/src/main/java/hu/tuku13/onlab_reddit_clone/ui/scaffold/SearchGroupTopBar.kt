package hu.tuku13.onlab_reddit_clone.ui.scaffold

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.screen.search_group.SearchGroupViewModel
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchGroupTopBar(
    navigationService: NavigationService,
    viewModel: SearchGroupViewModel
) {
    var searchText by remember { mutableStateOf("") }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    CenterAlignedTopAppBar(
        title = {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.search(it)
                },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleMedium,
                placeholder = {
                    Text(
                        text = "Search...",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.onSurface,
                    backgroundColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.search(searchText)
                        focusManager.clearFocus()
                        keyBoardController?.hide()
                    }
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigationService.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = {
                    searchText = ""
                    focusManager.clearFocus()
                    keyBoardController?.hide()
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Extended.surface2
        ),
    )
}