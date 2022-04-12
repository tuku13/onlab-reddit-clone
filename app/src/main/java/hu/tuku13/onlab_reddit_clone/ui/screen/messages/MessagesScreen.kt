package hu.tuku13.onlab_reddit_clone.ui.screen.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
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

fun formatDate(timeInMillis: Long): String {
    return SimpleDateFormat("yyyy.dd.MM", Locale.getDefault()).format(Date(timeInMillis))
}
