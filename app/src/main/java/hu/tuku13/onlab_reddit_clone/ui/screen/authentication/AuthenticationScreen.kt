package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
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
//                actions = {
//                    Icon(
//                        imageVector = Icons.Default.MoreVert,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.padding(end = 16.dp)
//                    )
//                },
                backgroundColor = Extended.surface2
            )
        },
        content = {
            when (authenticationState) {
                AuthenticationState.LOGIN -> LoginScreen(
                    onLogin = { },
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

