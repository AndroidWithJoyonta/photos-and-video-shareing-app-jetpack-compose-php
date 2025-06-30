package com.sharing.monio.presentation.screen.downloadScreen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sharing.monio.R
import com.sharing.monio.presentation.screen.homeScreen.ExoVideoPlayer
import com.sharing.monio.presentation.screenNavigation.Screens
import kotlin.collections.chunked

data class SavePhoto(
  val   uri : String ?=""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun DownloadScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val sharePref = context.getSharedPreferences("App", Context.MODE_PRIVATE)

    val showPhotomb = sharePref.getFloat("totalimageSize",0f)
    val showVideoMb = sharePref.getFloat("totalvideosize",0f)

    val showphotoCount = sharePref.getInt("updatedImagrTotalCount",0)
    val showvideoCount = sharePref.getInt("updatedVideoTotalCount",0)

    val total = showvideoCount + showphotoCount
    val totalmb = showPhotomb + showVideoMb



    val savePhoto = listOfNotNull(

        SavePhoto(sharePref.getString("img1",null)),
        SavePhoto(sharePref.getString("img2",null)),

    )


    val saveVideo = listOfNotNull(

        sharePref.getString("video1",null),
        sharePref.getString("video2",null)
        )

    Scaffold (topBar = {
        TopAppBar(title = {
            Text("Downloads", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        })
    }){

        Column (modifier.padding(it).fillMaxSize()){

          Column (modifier.padding(10.dp).fillMaxSize()){

              Text("Total", fontSize = 22.sp, fontWeight = FontWeight.Bold)
              Spacer(Modifier.height(10.dp))
              Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                  Text("$total Items", fontSize = 16.sp)
                  Text("$totalmb GB", fontSize = 16.sp)
              }


              Spacer(Modifier.height(20.dp))
              Text("Images", fontSize = 22.sp, fontWeight = FontWeight.Bold)
              Spacer(Modifier.height(10.dp))
              Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                  Text("$showphotoCount images", fontSize = 16.sp)
                  Text("$showPhotomb MB", fontSize = 16.sp)
              }


              LazyColumn(
                  contentPadding = PaddingValues(8.dp),
                  verticalArrangement = Arrangement.spacedBy(8.dp)

              ) {


                  items(savePhoto.chunked(2)) { rowItem ->

                      Row(
                          modifier = Modifier.fillMaxWidth(),
                          horizontalArrangement = Arrangement.spacedBy(8.dp),

                          ) {
                          rowItem.forEach { imageUri->


                              AsyncImage(
                                  model = ImageRequest.Builder(LocalContext.current)
                                      .data(imageUri.uri)
                                      .crossfade(true)
                                      .build(),
                                  contentDescription = null,
                                  modifier = modifier
                                      .weight(1f)
                                      .aspectRatio(1f)
                                      .clip(RoundedCornerShape(15.dp)).clickable{

                                      },
                                  contentScale = ContentScale.Crop,
                                  placeholder = painterResource(R.drawable.placeholder)
                              )







                          }
                          if (rowItem.size < 2) {

                              Spacer(modifier.weight(1f))
                          }

                      }
                  }
              }


              Spacer(Modifier.height(20.dp))
              Text("Videos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
              Spacer(Modifier.height(10.dp))
              Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                  Text("${showvideoCount} videos", fontSize = 16.sp)
                  Text("$showVideoMb MB", fontSize = 16.sp)
              }

              LazyColumn(
                  contentPadding = PaddingValues(8.dp),
                  verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.height(400.dp)
              ) {


                  items(saveVideo.chunked(2)) { rowItem ->
                      Row(
                          modifier = Modifier
                              .fillMaxWidth(),
                          horizontalArrangement = Arrangement.spacedBy(8.dp)
                      ) {
                          rowItem.forEach {
                              val uri = it


                              Column (modifier = Modifier.weight(1f)
                                  .aspectRatio(9f / 17f).clickable{


                                  }, horizontalAlignment = Alignment.CenterHorizontally){
                                  ExoVideoPlayer(
                                      videoUrl = uri,
                                      modifier = Modifier
                                          .weight(1f)
                                          .aspectRatio(9f / 16f).clickable{

                                          } // reels style portrait
                                  )
                                  Text("Click Preview")
                              }
                              // Text(it.video_url)

                              // Text(it.video_url)
                          }


                          // Fill empty space if only 1 video in the row
                          if (rowItem.size < 2) {
                              Spacer(
                                  modifier = Modifier
                                      .weight(1f)
                                      .aspectRatio(9f / 16f)
                              )
                          }
                      }
                  }
              }

          }
        }
    }

}