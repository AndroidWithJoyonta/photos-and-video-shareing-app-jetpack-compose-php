import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

fun shareMediaFile(context: Context, mediaUrl: String, extension: String) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL(mediaUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val file = File(context.cacheDir, "shared_file.$extension")
            val output = FileOutputStream(file)
            val input = connection.inputStream

            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }

            input.close()
            output.close()

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            withContext(Dispatchers.Main) {
                var type = when (extension) {
                    "mp3" -> "audio/mp3"
                    "mp4" -> "video/mp4"
                    else -> "*/*"
                }

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = type
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to share media", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
