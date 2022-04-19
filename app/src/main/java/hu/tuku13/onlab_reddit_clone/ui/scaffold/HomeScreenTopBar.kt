package hu.tuku13.onlab_reddit_clone.ui.scaffold

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun HomeScreenTopBar(
    navigationService: NavigationService
) {
    TopAppBar(
        title = {
            Text(
                text = "Home",
                style = MaterialTheme.typography.titleLarge
            )
        },
        elevation = 0.dp,
        backgroundColor = Extended.surface2,
        actions = {
            IconButton(onClick = { navigationService.navigate(Route.SearchGroupRoute) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    )
}