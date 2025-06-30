package com.sharing.monio.presentation.screen.homeScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.sharing.monio.data.api.PhotoModelItem
import com.sharing.monio.data.api.VideoItem
import com.sharing.monio.presentation.screen.Admob.BannerAdView
import com.sharing.monio.presentation.screenNavigation.Screens
import com.sharing.monio.presentation.viewModel.VideoViewModel

data class VideoItems(
    val url: String
)


@Composable

fun Videos(modifier: Modifier = Modifier,viewModel: VideoViewModel = viewModel()) {

    val videos by viewModel.categories

    val videoItem = listOf(
        VideoItems(
            url = "https://sai7755.com/video/5948574-sd_640_360_30fps.mp4"
        ),
        VideoItems(
            url = "https://sai7755.com/video/5948574-sd_640_360_30fps.mp4"
        ),
        VideoItems(
            url = "https://sai7755.com/video/one.mp4"
        )

    )


    val allVideo by viewModel.allVideo

    var seletectedCatgory by remember { mutableStateOf<VideoItem?>(null) }

    var isClick by remember { mutableStateOf(true) }


    var search by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var searchTxt = search.text


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {

        Column (modifier = Modifier.padding(10.dp)) {

            OutlinedTextField(
                value = search, onValueChange = {
                    search = it
                }, shape = RoundedCornerShape(10.dp), colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                ), label = {
                    Text("Search")
                }, leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        "",
                        tint = MaterialTheme.colorScheme.onSurface

                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }


        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(start = 10.dp)) {

            item {

                Column(
                    modifier
                        .width(100.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF4A90E2), Color(0xFF50E3C2)) // Blue to Turquoise
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("All", fontSize = 20.sp, modifier = Modifier.clickable {
                        isClick = true
                    })
                }
            }

            itemsIndexed(videos) { index, category ->
                Column(
                    modifier
                        .width(100.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF4A90E2), Color(0xFF50E3C2)) // Blue to Turquoise
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(category.category_name, fontSize = 20.sp, modifier = Modifier.clickable {
                        seletectedCatgory = category
                        isClick = false
                    })
                }
            }

        }

        Spacer(modifier.height(10.dp))

        val videoList = seletectedCatgory?.videos ?: emptyList()

        if (isClick){

            Box(Modifier.fillMaxWidth()) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    val filteredVideo = allVideo.filter { image ->
                        image.title.contains(searchTxt, ignoreCase = true) ||
                                image.description.contains(searchTxt, ignoreCase = true) ||
                                image.language.contains(searchTxt, ignoreCase = true) ||
                                image.tags.contains(searchTxt, ignoreCase = true)
                    }
                    items(filteredVideo.chunked(2)) { rowItem ->


                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItem.forEach {

                                val uri = "https://monioapp.com/photos/${it.video_url}"


                                Column(
                                    modifier = Modifier.weight(1f)
                                    .aspectRatio(9f / 17f).clickable {
                                        val encodedUri = Uri.encode(uri)
                                        val encodedUri1 = "null"

                                        GloableNavigation.navController.navigate("${Screens.ShareScreen.route}/$encodedUri1/$encodedUri")
                                    }, horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    ExoVideoPlayer(
                                        videoUrl = uri,
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(9f / 16f).clickable {

                                            } // reels style portrait
                                    )
                                    Text("Click Preview")
                                }
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(134.dp)
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                ) {
                    BannerAdView(LocalContext.current)
                }
            }

        }else {


            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.height(400.dp)
            ) {

                val filteredVideo = videoList.filter { image ->
                    image.title.contains(searchTxt, ignoreCase = true) ||
                            image.description.contains(searchTxt, ignoreCase = true) ||
                            image.language.contains(searchTxt, ignoreCase = true) ||
                            image.tags.contains(searchTxt, ignoreCase = true)
                }
                items(filteredVideo.chunked(2)) { rowItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItem.forEach {
                            val uri = "https://monioapp.com/photos/${it.video_url}"


                            Column (modifier = Modifier.weight(1f)
                                .aspectRatio(9f / 17f).clickable{
                                    val encodedUri = Uri.encode(uri)
                                    val encodedUri1 = "null"

                                    GloableNavigation.navController.navigate("${Screens.ShareScreen.route}/$encodedUri1/$encodedUri")
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