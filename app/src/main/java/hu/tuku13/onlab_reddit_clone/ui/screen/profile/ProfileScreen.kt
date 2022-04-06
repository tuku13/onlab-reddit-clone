package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.network.model.User
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        GlideImage(
            imageModel = "https://hatrabbits.com/wp-content/uploads/2017/01/random.jpg",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.wrapContentHeight()
        )

        InfoCard(
            User(
                id = 0L,
                name = "asd",
                bio = "asddddddddddddddddddddddddddddddddddddgfdgoiuwtrguintrwiugnrtwiugbtnrguirnub",
                profileImage = ""
            )
        )

        // TODO user postjai
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(
    user: User
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {

                GlideImage(
                    imageModel = if (user.profileImage == "") "https://picsum.photos/40" else user.profileImage,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clip(CircleShape)
                        .size(40.dp)
                )

                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                OutlinedButton(text = "Logout") {

                }

                FilledButton(text = "Edit Bio") {

                }
            }

            Text(
                text = user.bio,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
