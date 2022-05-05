package hu.tuku13.onlab_reddit_clone.domain.service

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_PICTURES
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import hu.tuku13.onlab_reddit_clone.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit
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