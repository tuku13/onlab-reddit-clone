package hu.tuku13.onlab_reddit_clone.ui.screen.authentication.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField

@Composable
fun RegisterScreen(
    toLoginScreen: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer: Observer<Boolean?> = Observer {
            if(it == true) {
                toLoginScreen()
            }
        }

        viewModel.success.observe(lifecycleOwner, observer)

        onDispose {
            viewModel.success.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Register",
                modifier = Modifier.align(Alignment.Start),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = "Username"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = "Email Address",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                keyboardType = KeyboardType.Password
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                text = "Login",
                onClick = toLoginScreen
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilledButton(
                text = "Create Account",
                onClick = {
                    if (password == confirmPassword) {
                        viewModel.register(username, password, emailAddress)
                    }
                }
            )

        }

    }

}