package hu.tuku13.onlab_reddit_clone.ui.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun GroupScreenTopBar(
    groupName: String,
    groupId: Long,
    navigationService: NavigationService
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = groupName,
                style = MaterialTheme.typography.titleLarge
            )
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
                            navigationService.navigate(
                                Route.EditGroupRoute(groupId)
                            )
                        }
                    )

                }
            }
        }
    )
}