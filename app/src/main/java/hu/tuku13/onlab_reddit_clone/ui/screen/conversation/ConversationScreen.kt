package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun ConversationScreen(
    partnerUserId: Long,
    partnerUserName: String,
    partnerProfileImageUrl: String,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    val messages = viewModel.messages.observeAsState()
    val isRefreshing = viewModel.isRefreshing.observeAsState()


    LaunchedEffect(key1 = Any()) {
        viewModel.partnerUserId = partnerUserId
        viewModel.getMessages()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value!!),
            onRefresh = { viewModel.refreshMessages() },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                reverseLayout = true,
            ) {

                items(messages.value?.size ?: 0) { index ->
                    var shouldShowTimestamp = false

                    if(index == messages.value!!.size - 1 ) {
                        shouldShowTimestamp = true
                    } else {
                        val previousTimestamp = messages.value!![index].timestamp
                        val currentTimestamp = messages.value!![index + 1].timestamp

                        if(previousTimestamp - currentTimestamp > (10 * 60 * 1000L)) {
                            shouldShowTimestamp = true
                        }
                    }

                    ChatBubble(
                        message = messages.value!![index],
                        partnerUserId = partnerUserId,
                        partnerProfileImage = partnerProfileImageUrl,
                    )

                    if (shouldShowTimestamp) {
                        Text(
                            text = formatTime(messages.value!![index].timestamp),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        MessageInputBar(
            onSubmit = { viewModel.sendMessage(partnerUserName, it) }
        )
    }
}

private fun formatTime(timeInMillis: Long): String {
    val current = Calendar.getInstance()

    val dayFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    return if(dayFormat.format(Date(timeInMillis)) == dayFormat.format(Date(current.timeInMillis))) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timeInMillis))
    } else {
        SimpleDateFormat("EEE HH:mm", Locale.getDefault()).format(Date(timeInMillis))
    }

}
