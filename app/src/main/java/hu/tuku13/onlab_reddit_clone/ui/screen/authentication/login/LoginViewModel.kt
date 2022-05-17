package hu.tuku13.onlab_reddit_clone.ui.screen.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
) : ViewModel() {
    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationService.login(username, password)
        }
    }
}