import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.sharing.monio.presentation.screen.sharephotoandVideo.SaveImageVideoSize
import com.sharing.monio.presentation.screen.sharephotoandVideo.fetchFileSizeInMB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun downloadImageWithSize(context: Context, imageUrl: String, fileName: String = "downloaded_image.jpg") {
    CoroutineScope(Dispatchers.Main).launch {
        val size = fetchFileSizeInMB(imageUrl)
        if (size != null) {
            Toast.makeText(context, "File size: $size", Toast.LENGTH_LONG).show()
            val count = 1
            SaveImageVideoSize(context,size.replace(" MB","").toFloat(), 0F,1,0)
        } else {
            Toast.makeText(context, "File size not available", Toast.LENGTH_SHORT).show()
        }

        // Proceed with download after showing size
        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle("Downloading image")
            .setDescription("Downloading file: $fileName ($size)")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()
    }
}
