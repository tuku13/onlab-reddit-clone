package hu.tuku13.onlab_reddit_clone.ui.screen.authentication

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.ui.components.TextField

const val TAG = "LoginScreen"

@Composable
fun LoginScreen() {
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


        // TODO password type
        TextField(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { Log.d(TAG, "Register") },
                shape = RoundedCornerShape(100.0f),
                colors = ButtonDefaults.outlinedButtonColors(),
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Button(
                onClick = { Log.d(TAG, "Login") },
                shape = RoundedCornerShape(100.0f),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}