package hu.tuku13.onlab_reddit_clone.ui.components

import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import hu.tuku13.onlab_reddit_clone.ui.screen.authentication.TAG

//TODO 3 féle gomb kék fehér ,zöld

@Composable
fun FilledButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.0f),
        colors = ButtonDefaults.buttonColors()
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
        colors = ButtonDefaults.outlinedButtonColors(),
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