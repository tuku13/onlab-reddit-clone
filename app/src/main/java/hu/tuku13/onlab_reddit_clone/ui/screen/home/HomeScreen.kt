package hu.tuku13.onlab_reddit_clone.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton

@Composable
fun HomeScreen(
    onLogout : () -> Unit
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Text("Home Screen")

        Spacer(modifier = Modifier.height(8.dp))

        TonalButton(
            text = "Logout",
            onClick = onLogout
        )
    }
}