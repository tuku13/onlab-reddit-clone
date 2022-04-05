package hu.tuku13.onlab_reddit_clone.ui.screen.create_group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.R
import hu.tuku13.onlab_reddit_clone.ui.components.FilledButton
import hu.tuku13.onlab_reddit_clone.ui.components.OutlinedButton
import hu.tuku13.onlab_reddit_clone.ui.components.TextField
import hu.tuku13.onlab_reddit_clone.ui.components.TonalButton

@Composable
fun CreateGroupScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        var groupName by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var imageUrl by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = groupName,
            label = "Group name",
            onValueChange = { groupName = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = description,
            label = "Description",
            onValueChange = { description = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        ImageUploader(
            onUpload = { imageUrl = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            OutlinedButton(
                text = "Cancel",
                onClick = {}
            )

            Spacer(modifier = Modifier.width(8.dp))

            TonalButton(
                text = "Create",
                onClick = {}
            )
        }

    }
}

//TODO darkmodeban a placeholder kép jobb és bal szélén sem jó a border

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageUploader(
    onUpload: (String) -> Unit
) {
    Card(
        modifier = Modifier.size(360.dp).wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Group Image",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Box(modifier = Modifier.width(360.dp).height(200.dp)) {
                GlideImage(
                    imageModel = Image(
                        painter = painterResource(R.drawable.upload_image_placeholder),
                        contentDescription = "Uploaded image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(360.dp)
                    ),
                    modifier = Modifier.width(360.dp)
                )
            }

            Row(
                modifier = Modifier.height(72.dp).fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    text = "Delete",
                    onClick = { }
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilledButton(
                    text = "Edit",
                    onClick = { }
                )

            }

        }
    }
}
