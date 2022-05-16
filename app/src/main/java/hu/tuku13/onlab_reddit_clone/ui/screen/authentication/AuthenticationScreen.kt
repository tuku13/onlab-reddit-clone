package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AuthenticationScreen() {
    var authenticationState by remember { mutableStateOf(AuthenticationState.LOGIN) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = authenticationState.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                elevation = 0.dp,
                backgroundColor = Extended.surface2
            )
        },
        content = {
            when (authenticationState) {
                AuthenticationState.LOGIN -> LoginScreen(
                    onRegister = { authenticationState = AuthenticationState.REGISTER }
                )
                AuthenticationState.REGISTER -> RegisterScreen(
                    onLogin = { authenticationState = AuthenticationState.LOGIN },
                    onRegister = { }
                )
            }
        }
    )
}

