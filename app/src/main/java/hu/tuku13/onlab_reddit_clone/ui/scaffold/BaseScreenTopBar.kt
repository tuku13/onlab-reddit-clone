package hu.tuku13.onlab_reddit_clone.ui.scaffold

import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun BaseScreenTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        elevation = 0.dp,
        backgroundColor = Extended.surface2
    )
}