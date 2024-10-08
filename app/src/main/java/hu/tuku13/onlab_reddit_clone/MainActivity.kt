package hu.tuku13.onlab_reddit_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import dagger.hilt.android.AndroidEntryPoint
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.screen.MainScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.authentication.AuthenticationScreen
import hu.tuku13.onlab_reddit_clone.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authenticationService: AuthenticationService

    @Inject
    lateinit var navigationService: NavigationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val userId = authenticationService.userId.observeAsState()

                if (userId.value != 0L) {
                    MainScreen(authenticationService, navigationService)
                } else {
                    AuthenticationScreen()
                }
            }
        }
    }
}