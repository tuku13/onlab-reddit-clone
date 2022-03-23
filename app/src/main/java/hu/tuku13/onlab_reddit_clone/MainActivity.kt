package hu.tuku13.onlab_reddit_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import hu.tuku13.onlab_reddit_clone.ui.screen.authentication.AuthenticationScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.home.HomeScreen
import hu.tuku13.onlab_reddit_clone.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                var isAuthenticated by remember { mutableStateOf(false) }

                if (isAuthenticated) {
                    HomeScreen(
                        onLogout = { isAuthenticated = false }
                    )
                } else {
                    AuthenticationScreen()
                }
            }
        }
    }
}