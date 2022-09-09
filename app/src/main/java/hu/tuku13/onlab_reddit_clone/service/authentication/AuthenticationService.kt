package hu.tuku13.onlab_reddit_clone.service.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.tuku13.onlab_reddit_clone.network.model.LoginForm
import hu.tuku13.onlab_reddit_clone.network.model.RegisterForm
import hu.tuku13.onlab_reddit_clone.service.api.AuthenticationApi
import hu.tuku13.onlab_reddit_clone.service.api.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import javax.inject.Inject

const val TAG = "AuthenticationService"

class AuthenticationService @Inject constructor(
    private val api: AuthenticationApi
) {
    private var _userId: MutableLiveData<Long> = MutableLiveData(0)
    val userId: LiveData<Long>
        get() = _userId

    private var _token: MutableLiveData<String> = MutableLiveData("")
    val token: LiveData<String>
        get() = _token

    suspend fun login(username: String, password: String): NetworkResult<Unit> {
        return try {
            val response = api.login(
                LoginForm(
                    username = username,
                    password = password
                )
            )

            return if (response.isSuccessful) {
                _userId.postValue(response.body()!!.userId)
                _token.postValue(response.body()!!.token)
                NetworkResult.Success(Unit)
            } else {
                Log.d(TAG, "login error")
                NetworkResult.Error(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }

    fun logout() {
        _userId.postValue(0)
        _token.postValue("")
    }

    suspend fun register(
        username: String,
        password: String,
        confirmPassword: String,
        email: String
    ): NetworkResult<Unit> {
        return try {
            val form = RegisterForm(
                username = username,
                password = password,
                confirmPassword = confirmPassword,
                emailAddress = email
            )

            val response = api.register(form)

            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(Exception("${response.errorBody()}"))
            }
        } catch (e: Exception) {
            NetworkResult.Error(e)
        }
    }
}