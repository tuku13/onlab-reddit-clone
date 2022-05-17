package hu.tuku13.onlab_reddit_clone.ui.screen.other_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.scaffold.SubScreenTopBar
import hu.tuku13.onlab_reddit_clone.ui.screen.profile.ProfileScreen
import hu.tuku13.onlab_reddit_clone.ui.screen.profile.ProfileViewModel

@Composable
fun OtherProfileScreen(
    userId: Long,
    navigationService: NavigationService,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user.observeAsState()

    Column {
        SubScreenTopBar(
            title = user.value?.let { "${it.name}'s Profile" } ?: "Profile",
            navigationService = navigationService
        )
        ProfileScreen(
            userId = userId,
            navigationService = navigationService
        )
    }
}