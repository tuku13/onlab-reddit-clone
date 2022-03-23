package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton

const val TAG = "LoginScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
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
            value = password,
            onValueChange = { password = it },
            label = "Password",
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                text = "Register",
                onClick = onRegister
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilledButton(
                text = "Login",
                onClick = onLogin
            )

            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}