package hu.tuku13.onlab_reddit_clone.ui.screen.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
) : ViewModel() {

}