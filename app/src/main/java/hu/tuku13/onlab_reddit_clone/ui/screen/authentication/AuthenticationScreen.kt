package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import androidx.compose.foundation.background
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AuthenticationScreen(
    onAuthentication: () -> Unit = {}
) {
    var authenticationState by remember { mutableStateOf(AuthenticationState.LOGIN) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = authenticationState.title) },
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
            )
        },
        content = {
            when (authenticationState) {
                AuthenticationState.LOGIN -> LoginScreen(
                    onLogin = onAuthentication,
                    onRegister = { authenticationState = AuthenticationState.REGISTER }
                )
                AuthenticationState.REGISTER -> RegisterScreen(
                    onLogin = { authenticationState = AuthenticationState.LOGIN },
                    onRegister = {  }
                )
            }
        }

    )

}

enum class AuthenticationState(val title: String) {
    LOGIN("Login"),
    REGISTER("Register")
}
