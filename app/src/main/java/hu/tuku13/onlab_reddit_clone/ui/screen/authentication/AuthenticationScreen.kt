package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.theme.surface2

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
                title = {
                    Text(
                        text = authenticationState.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                elevation = 0.dp,
                actions = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                backgroundColor = surface2 // MaterialTheme.colorScheme.surfaceVariant
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
                    onRegister = { }
                )
            }
        }


    )

}

enum class AuthenticationState(val title: String) {
    LOGIN("Login"),
    REGISTER("Register")
}
