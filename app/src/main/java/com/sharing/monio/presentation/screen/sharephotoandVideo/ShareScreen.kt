package com.sharing.monio.presentation.screen.sharephotoandVideo

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sharing.monio.R
import com.sharing.monio.presentation.screen.Admob.BannerAdView
import com.sharing.monio.presentation.screen.Admob.LoadAndShowInterstitial
import com.sharing.monio.presentation.screen.homeScreen.ExoVideoPlayer
import com.sharing.monio.presentation.viewModel.SignUpViewmodel
import downloadImageWithSize
import shareMediaFile


@Composable
fun ShareScreen( imageUri: String,
                 videoUri: String,videoModel : SignUpViewmodel = viewModel()
) {

    val context = LocalContext.current

    Scaffold(topBar = {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(Modifier.width(10.dp))
            Icon(imageVector = Icons.Default.ArrowBack, "", modifier = Modifier.clickable {
                GloableNavigation.navController.popBackStack()
            })
            Spacer(Modifier.width(10.dp))
            Text("Preview", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(), verticalArrangement = Arrangement.Center
        )
        {

            if (imageUri != "null") {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder)
                )

                Spacer(Modifier.height(100.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    Button(
                        onClick = {
                            LoadAndShowInterstitial(context)
                            val fileName = Uri.parse(imageUri).lastPathSegment ?: "image.jpg"
                            downloadImageWithSize(context, imageUri, fileName)
                            SaveImage(context,imageUri)
                            videoModel.download_count(context)
                            //SaveImageVideoSize(context,0f, 0F,1,0)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.width(120.dp).border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Text("Download", color = MaterialTheme.colorScheme.onSurface)
                    }


                    Spacer(Modifier.width(10.dp))

                    Button(
                        onClick = {
                            LoadAndShowInterstitial(context)
                            shareImageFromUrl(context, imageUri)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.width(120.dp).border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Text("Share", color = MaterialTheme.colorScheme.onSurface)

                    }
                }
            }else{

                ExoVideoPlayer(
                    videoUrl = videoUri,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(7f / 10f) // reels style portrait
                )

                Spacer(Modifier.height(70.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    Button(
                        onClick = {
                            LoadAndShowInterstitial(context)
                            val fileName = Uri.parse(imageUri).lastPathSegment ?: "image.jpg"
                            downloadFileWithSize(
                                context,
                                videoUri,
                                fileName,
                                onSizeFetched = { size ->
                                    Toast.makeText(context, "Video size: $size", Toast.LENGTH_SHORT)
                                        .show()
                                    val count = 0
                                    SaveImageVideoSize(context, 0F, size.replace(" MB", "").toFloat(),0,1)
                                    SaveVideo(context,videoUri)
                                    videoModel.download_count(context)


                                },
                                onStartDownload = {
                                    Toast.makeText(context, "Download started!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.width(120.dp).border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Text("Download", color = MaterialTheme.colorScheme.onSurface)

                    }


                    Spacer(Modifier.width(10.dp))

//                    Button(
//                        onClick = {
//
//                            shareMediaFile(context, videoUri,"mp4")
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Transparent
//                        ),
//                        modifier = Modifier.width(120.dp).border(
//                            width = 1.dp,
//                            color = MaterialTheme.colorScheme.onSurface,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                    ) {
//                        Text("Share", color = MaterialTheme.colorScheme.onSurface)
//                    }}
                }
            }



                BannerAdView(LocalContext.current)


        }
    }

}

fun SaveImage(context: Context,newImageuri: String){

    val sharePref = context.getSharedPreferences("App", Context.MODE_PRIVATE)
    val editor = sharePref.edit()

    when {

        sharePref.getString("img1",null) == null -> editor.putString("img1",newImageuri)
        sharePref.getString("img2",null) == null -> editor.putString("img2",newImageuri)
        sharePref.getString("img3",null) == null -> editor.putString("img3",newImageuri)
        sharePref.getString("img4",null) == null -> editor.putString("img4",newImageuri)


        else ->{
            editor.putString("img1",newImageuri)
            editor.putString("img2",null)
            editor.putString("img3",null)
            editor.putString("img4",null)
        }
    }

    editor.apply()

}
fun SaveVideo(context: Context,newImageuri: String){

    val sharePref = context.getSharedPreferences("App", Context.MODE_PRIVATE)
    val editor = sharePref.edit()

    when {

        sharePref.getString("video1",null) == null -> editor.putString("video1",newImageuri)
        sharePref.getString("video2",null) == null -> editor.putString("video2",newImageuri)
        sharePref.getString("video3",null) == null -> editor.putString("video3",newImageuri)
        sharePref.getString("video4",null) == null -> editor.putString("video4",newImageuri)


        else ->{
            editor.putString("video1",newImageuri)
            editor.putString("video2",null)
            editor.putString("video3",null)
            editor.putString("video4",null)
        }
    }

    editor.apply()

}


fun SaveImageVideoSize(context: Context,imageSize: Float, videoSize: Float,imageCount: Int,videoCount: Int){

    val sharePref = context.getSharedPreferences("App", Context.MODE_PRIVATE)



    val addPhoto = sharePref.getFloat("imageSize",0f)
    val addVideo = sharePref.getFloat("vidoeSize",0f)

    val imageCounts = sharePref.getInt("imageCount",0)
    val videoCounts = sharePref.getInt("videoCount",0)

    val existingImageTotal = addPhoto + imageSize
    val existingVideoTotal =  addVideo + videoSize

    val existingImageTotalCount =  imageCounts + imageCount
    val existingVideoTotalCount =  videoCounts + videoCount

    // Add new values to existing totals
    val updatedImageTotal = existingImageTotal + imageSize
    val updatedVideoTotal = existingVideoTotal + videoSize

    val updatedImagrTotalCount = existingImageTotalCount + imageCount
    val updatedVideoTotalCount = existingVideoTotalCount + videoCount

    // Save current sizes and totals
    with(sharePref.edit()) {
        putFloat("imageSize", imageSize)
        putFloat("videoSize", videoSize)
        putInt("imageCount", imageCount)
        putInt("videoCount", videoCount)
        putFloat("totalimageSize", updatedImageTotal)
        putFloat("totalvideosize", updatedVideoTotal)
        putInt("updatedImagrTotalCount", updatedImagrTotalCount)
        putInt("updatedVideoTotalCount", updatedVideoTotalCount)
        apply()
    }
}