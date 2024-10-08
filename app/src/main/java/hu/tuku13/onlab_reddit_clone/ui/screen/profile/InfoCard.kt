package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import hu.tuku13.onlab_reddit_clone.domain.model.User
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.ProfileImage
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(
    user: User,
    isMineProfile: Boolean,
    onLogout: () -> Unit,
    onSendMessage: (User) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = Extended.surface2,
        shape = RectangleShape
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                ProfileImage(user)

                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                if (isMineProfile) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(text = "Logout") {
                            onLogout()
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        FilledButton(text = "Edit Bio") {

                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledButton(
                            text = "Send Message",
                            onClick = { onSendMessage(user) }
                        )
                    }
                }
            }

            Text(
                text = user.bio.ifBlank { "An empty profile" },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

