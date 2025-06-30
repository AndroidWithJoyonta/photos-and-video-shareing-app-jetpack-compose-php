package com.sharing.monio.presentation.screen.sharephotoandVideo

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
import java.net.URL

fun shareImageFromUrl(context: Context, imageUrl: String) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL(imageUrl)
            val connection = url.openConnection()
            connection.connect()
            val input = connection.getInputStream()

            val fileName = Uri.parse(imageUrl).lastPathSegment ?: "shared_image.jpg"
            val cacheDir = File(context.cacheDir, "images")
            cacheDir.mkdirs()
            val file = File(cacheDir, fileName)

            file.outputStream().use { output ->
                input.copyTo(output)
            }

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            withContext(Dispatchers.Main) {
                context.startActivity(
                    Intent.createChooser(shareIntent, "Share Image")
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to share image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
