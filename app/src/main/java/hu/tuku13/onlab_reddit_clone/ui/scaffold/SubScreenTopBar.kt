package hu.tuku13.onlab_reddit_clone.ui.scaffold

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun SubScreenTopBar(title: String, navigationService: NavigationService) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigationService.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        elevation = 0.dp,
        backgroundColor = Extended.surface2
    )
}