package hu.tuku13.onlab_reddit_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.ui.screen.MainScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.authentication.AuthenticationScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.home.HomeScreen
import hu.tuku13.onlab_reddit_clone.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authenticationService: AuthenticationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val userId = authenticationService.userId.observeAsState()

                if (userId.value != 0L) {
                    MainScreen()
                } else {
                    AuthenticationScreen()
                }
            }
        }
    }
}