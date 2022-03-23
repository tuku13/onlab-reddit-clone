package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField

@Composable
fun RegisterScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = "Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = "Username",
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                text = "Login",
                onClick = onLogin
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilledButton(
                text = "Create Account",
                onClick = onRegister
            )

        }
    }
}