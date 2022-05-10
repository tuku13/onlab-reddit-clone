package hu.tuku13.onlab_reddit_clone.ui.screen.search_group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import hu.tuku13.onlab_reddit_clone.navigation.Route

@Composable
fun SearchGroupScreen(
    navigationService: NavigationService,
    viewModel: SearchGroupViewModel = hiltViewModel()
) {
    val groups = viewModel.groups.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (groups.value.isEmpty()) {
            Text(
                text = "Could not find group.",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(groups.value.size) { index ->
                    val group = groups.value[index]

                    Spacer(modifier = Modifier.height(16.dp))

                    GroupCard(
                        group = group,
                        onClick = {
                            navigationService.navigate(
                                Route.GroupRoute(
                                groupId = group.id,
                                groupName = group.name
                            ))
                        }
                    )

                }
            }
        }
    }
}

