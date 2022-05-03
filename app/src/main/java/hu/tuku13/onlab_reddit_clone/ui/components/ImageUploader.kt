package hu.tuku13.onlab_reddit_clone.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageUploader(
    onUpload: (Uri?) -> Unit
) {
    Card(
        modifier = Modifier
            .size(360.dp)
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            imageUri = uri
            onUpload(uri)
        }

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

            Box(
                modifier = Modifier
                    .width(360.dp)
                    .height(200.dp)
            ) {
                if(imageUri != null) {
                    GlideImage(
                        imageModel = imageUri,
                        modifier = Modifier.width(360.dp),
                        contentDescription = "Uploaded image",
                        contentScale = ContentScale.FillWidth,
                    )
                } else {
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
            }

            Row(
                modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    text = "Delete",
                    onClick = { imageUri = null }
                )

                Spacer(modifier = Modifier.width(8.dp))

                FilledButton(
                    text = "Upload",
                    onClick = { launcher.launch("image/jpeg") }
                )

            }

        }
    }
}