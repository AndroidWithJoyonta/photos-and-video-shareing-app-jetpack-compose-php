package com.sharing.monio.presentation.screen.sharephotoandVideo

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

fun downloadFileWithSize(
    context: Context,
    url: String,
    fileName: String,
    onSizeFetched: (String) -> Unit,
    onStartDownload: () -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connect()
            val contentLength = connection.contentLengthLong
            connection.disconnect()

            val sizeInMB = contentLength.toDouble() / (1024 * 1024)
            val sizeFormatted = String.format("%.2f MB", sizeInMB)

            withContext(Dispatchers.Main) {
                onSizeFetched(sizeFormatted)

                val request = DownloadManager.Request(Uri.parse(url)).apply {
                    setTitle("Downloading $fileName")
                    setDescription("Size: $sizeFormatted")
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                    setAllowedOverMetered(true)
                    setAllowedOverRoaming(true)
                }

                val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)

                onStartDownload()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to fetch file size or download", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
