package hu.tuku13.onlab_reddit_clone.ui.screen.messages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.network.model.Contact
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessagesScreen(
    navigationService: NavigationService,
    viewModel: MessagesViewModel = hiltViewModel()
) {
    val contacts = viewModel.contacts.observeAsState()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(contacts.value?.size ?: 0) { index ->
            val contact = contacts.value!![index]
            val profileImageUrl = if (contact.profileImageUrl != "") contact.profileImageUrl else "https://picsum.photos/40"

            ContactCard(
                contact = contact,
                onClick = {
                    navigationService.navigateTo(Route.ConversationRoute(
                        partnerUserId = it,
                        partnerUserName = contact.name,
                        partnerProfileImageUrl = profileImageUrl
                    ))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    contact: Contact,
    onClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            .clickable { onClick(contact.userId) },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        containerColor = MaterialTheme.colorScheme.background,
        elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
    ) {

        Row(
            modifier = Modifier
                .width(352.dp)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = if (contact.profileImageUrl == "") "https://picsum.photos/40" else contact.profileImageUrl,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .size(40.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .height(80.dp)
                    .weight(1.0f)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = contact.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .height(80.dp)
                    .wrapContentWidth()
                    .padding(end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = formatDate(contact.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

    }
}

fun formatDate(timeInMillis: Long): String {
    return SimpleDateFormat("yyyy.dd.MM", Locale.getDefault()).format(Date(timeInMillis))
}
