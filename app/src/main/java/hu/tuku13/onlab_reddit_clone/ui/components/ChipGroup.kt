package hu.tuku13.onlab_reddit_clone.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hu.tuku13.onlab_reddit_clone.domain.model.PostSorting

@Composable
fun ChipGroup(
    sorting: PostSorting,
    newOnClick: () -> Unit,
    topOnClick: () -> Unit,
    trendingOnClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    ) {
        Chip(
            icon = Icons.Outlined.StarOutline,
            text = "New",
            selected = sorting == PostSorting.NEW,
            onClick = newOnClick
        )

        Chip(
            icon = Icons.Outlined.TrendingUp,
            text = "Top",
            selected = sorting == PostSorting.TOP,
            onClick = topOnClick
        )

        Chip(
            icon = Icons.Outlined.LocalFireDepartment,
            text = "Trending",
            selected = sorting == PostSorting.TRENDING,
            onClick = trendingOnClick
        )
    }
}