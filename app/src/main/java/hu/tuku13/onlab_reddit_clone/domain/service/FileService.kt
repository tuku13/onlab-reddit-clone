package hu.tuku13.onlab_reddit_clone.domain.service

import android.content.Context
import android.net.Uri
import android.util.Log
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FileService @Inject constructor(
    private val context: Context
) {
    suspend fun loadImage(uri: Uri): NetworkResult<ByteArray> {
        return try {
            val resolver = context.contentResolver

            withContext(Dispatchers.IO) {
                val bytes = resolver.openInputStream(uri)?.readBytes()
                    ?: return@withContext NetworkResult.Error(Exception(""))
                return@withContext NetworkResult.Success(bytes)
            }

        } catch (e: Exception) {
            Log.d("FileService", e.toString())
            return NetworkResult.Error(e)
        }
    }
}