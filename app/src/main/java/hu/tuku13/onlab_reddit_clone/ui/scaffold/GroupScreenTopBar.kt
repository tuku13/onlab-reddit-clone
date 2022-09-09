package hu.tuku13.onlab_reddit_clone.ui.scaffold

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.service.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.screen.edit_group.EditGroupViewModel
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun GroupScreenTopBar(
    navigationService: NavigationService,
    viewModel: EditGroupViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val group = viewModel.group.observeAsState()
    var isMenuOpen by remember { mutableStateOf(false) }

    Log.d("TopBar, topbar", "$viewModel")

    DisposableEffect(lifecycleOwner) {
        val groupObserver: Observer<Group?> = Observer {
            Log.d("TopBar", "group: $it")
        }

        viewModel.group.observe(lifecycleOwner, groupObserver)

        onDispose {
            viewModel.group.removeObserver(groupObserver)
        }
    }

    TopAppBar(
        title = {
            Text(
                text = group.value?.name ?: "",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigationService.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        elevation = 0.dp,
        backgroundColor = Extended.surface2,
        actions = {
            Box {
                IconButton(onClick = { isMenuOpen = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }

                DropdownMenu(
                    expanded = isMenuOpen,
                    onDismissRequest = { isMenuOpen = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Edit",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            group.value?.let {
                                navigationService.navigate(
                                    Route.EditGroupRoute(it.id)
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}