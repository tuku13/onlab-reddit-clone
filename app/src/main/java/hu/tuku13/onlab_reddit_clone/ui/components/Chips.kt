package hu.tuku13.onlab_reddit_clone.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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

@Composable
fun Chip(
    icon: ImageVector,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (selected) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f) else MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(32.dp)
                .wrapContentWidth()
                .padding(start = 8.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}