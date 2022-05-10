package hu.tuku13.onlab_reddit_clone.domain.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.tuku13.onlab_reddit_clone.network.model.LoginForm
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import javax.inject.Inject

const val TAG = "AuthenticationService"

class AuthenticationService @Inject constructor(
    private val api: RedditCloneApi
) {
    private var _userId: MutableLiveData<Long> = MutableLiveData(0)
    val userId: LiveData<Long>
        get() = _userId

    suspend fun login(username: String, password: String) {
        val response = api.login(
            LoginForm(
                username = username,
                password = password
            )
        )

        if(response.isSuccessful) {
            _userId.postValue(response.body())
        } else {
            Log.d(TAG, "login error")
        }
    }

    fun logout() {
        _userId.value = 0
    }

    suspend fun register() {
        // TODO
    }
}