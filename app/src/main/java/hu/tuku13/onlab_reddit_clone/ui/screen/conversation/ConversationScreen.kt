package hu.tuku13.onlab_reddit_clone.ui.screen.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ConversationScreen(
    partnerUserId: Long,
    navController: NavController,
    viewModel: ConversationViewModel = hiltViewModel()
) {
    val messages = viewModel.messages.observeAsState()
    val isRefreshing = viewModel.isRefreshing.observeAsState()

    LaunchedEffect(key1 = Any()) {
        viewModel.refreshMessages(partnerUserId)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value!!),
            onRefresh = { viewModel.refreshMessages(partnerUserId) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                reverseLayout = true,

            ) {

                items(messages.value?.size ?: 0) { index ->
                    ChatBubble(
                        message = messages.value!![index],
                        partnerUserId = partnerUserId
                    )
                }
            }
        }

//        Spacer(modifier = Modifier.height(16.dp))

        MessageInputBar()
    }
}


