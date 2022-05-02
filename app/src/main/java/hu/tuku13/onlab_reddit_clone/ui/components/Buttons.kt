package hu.tuku13.onlab_reddit_clone.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilledButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.0f),
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(100.0f),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun TonalButton(
    text: String,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = onClick,
        shape = RoundedCornerShape(100.0f),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}