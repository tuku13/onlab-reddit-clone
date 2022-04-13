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

@Composable
fun ChipGroup(
    newOnClick: () -> Unit,
    topOnClick: () -> Unit,
    trendingOnClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    ) {
        var selectedId by remember { mutableStateOf(0) }

        Chip(
            icon = Icons.Outlined.StarOutline,
            text = "New",
            selected = selectedId == 0,
            onClick = {
                selectedId = 0
                newOnClick()
            }
        )

        Chip(
            icon = Icons.Outlined.TrendingUp,
            text = "Top",
            selected = selectedId == 1,
            onClick = {
                selectedId = 1
                topOnClick()
            }
        )

        Chip(
            icon = Icons.Outlined.LocalFireDepartment,
            text = "Trending",
            selected = selectedId == 2,
            onClick = {
                selectedId = 2
                trendingOnClick()
            }
        )
    }
}