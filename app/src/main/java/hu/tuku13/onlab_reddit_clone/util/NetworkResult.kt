package hu.tuku13.onlab_reddit_clone.util

import java.lang.Exception

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}