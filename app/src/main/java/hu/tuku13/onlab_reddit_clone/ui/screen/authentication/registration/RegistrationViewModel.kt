package hu.tuku13.onlab_reddit_clone.ui.screen.authentication.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tuku13.onlab_reddit_clone.network.model.RegisterForm
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private val _success: MutableLiveData<Boolean?> = MutableLiveData(null)
    val success: LiveData<Boolean?>
        get() = _success

    fun register(username: String, password: String, emailAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authenticationService.register(
                username = username,
                email = emailAddress,
                password = password,
                confirmPassword = password
            )
            when (response) {
                is NetworkResult.Success -> _success.postValue(true)
                is NetworkResult.Error -> Log.d("RegistrationVM", response.exception.toString())
            }
        }
    }
}