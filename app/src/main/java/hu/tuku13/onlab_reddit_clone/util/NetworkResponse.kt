package hu.tuku13.onlab_reddit_clone.util

sealed class NetworkResponse<out T: Any>

class NetworkResult<K: Any>(val value: K) : NetworkResponse<K>()

class NetworkErrorResult(val exception: Exception) : NetworkResponse<Nothing>()