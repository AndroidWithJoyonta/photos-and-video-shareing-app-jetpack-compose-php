package com.sharing.monio.presentation.screen.sharephotoandVideo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

suspend fun fetchFileSizeInMB(url: String): String? {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connect()
            val contentLength = connection.contentLengthLong
            connection.disconnect()

            if (contentLength > 0) {
                val sizeInMB = contentLength.toDouble() / (1024 * 1024)
                String.format("%.2f MB", sizeInMB)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
