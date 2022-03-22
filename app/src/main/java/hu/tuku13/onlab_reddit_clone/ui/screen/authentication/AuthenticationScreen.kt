package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import androidx.compose.foundation.background
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AuthenticationScreen() {
    val authenticationState by remember { mutableStateOf(AuthenticationState.LOGIN) }

    Scaffold(
        topBar = {
                 TopAppBar(
                     title = { Text(text = authenticationState.title) },
                     modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                 ) 
        },
        content = {
            when (authenticationState) {
                AuthenticationState.LOGIN -> LoginScreen()
                AuthenticationState.REGISTER -> RegisterScreen()
            }
        }

    )

}

enum class AuthenticationState(val title: String) {
    LOGIN("Login"),
    REGISTER("Register")
}
