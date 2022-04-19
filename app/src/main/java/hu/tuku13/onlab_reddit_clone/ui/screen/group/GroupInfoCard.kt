package hu.tuku13.onlab_reddit_clone.ui.screen.group

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.domain.model.Group
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@Composable
fun GroupInfoCard(group: Group) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        var description by remember {
            mutableStateOf(
                if (group.description.length > 50) "${group.description.take(50)}..." else group.description
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            GlideImage(
                imageModel = group.groupImageUrl,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.heightIn(max = 256.dp),
            )
        }

        Row(
            modifier = Modifier
                .background(Extended.surface2)
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1.0f)
            ) {
                Row {
                    GlideImage(
                        imageModel = if (group.groupImageUrl != "") group.groupImageUrl else "https://picsum.photos/40",
                        modifier = Modifier.clip(CircleShape).size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = group.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${group.members} members",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledButton(text = "Join") {
                    // TODO feliratkozás csoportra
                }

                if (description.length <= 53) {
                    TonalButton(text = "Read more") {
                        description = group.description
                    }
                }

            }
        }
    }
}