package com.sharing.monio.presentation.screen.homeScreen

import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sharing.monio.R
import com.sharing.monio.presentation.screen.Admob.BannerAdView
import com.sharing.monio.presentation.screen.Admob.LoadAndShowInterstitial
import com.sharing.monio.presentation.screen.Admob.NativeAdView

import com.sharing.monio.presentation.screenNavigation.Screens
import com.sharing.monio.presentation.ui.theme.black
import com.sharing.monio.presentation.ui.theme.blue
import com.sharing.monio.presentation.ui.theme.gray
import com.sharing.monio.presentation.ui.theme.white
import com.sharing.monio.presentation.viewModel.PhotoViewModel
import com.sharing.monio.presentation.viewModel.VideoViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.collections.chunked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun HomeScreen(viewModel: PhotoViewModel = viewModel(),viewModel2: VideoViewModel = viewModel()) {

    val allImage by viewModel.allPhotos
    val allVideo by viewModel2.allVideo

    val context = LocalContext.current
    Scaffold(bottomBar = {



    }, topBar = {
        TopAppBar(title = { Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = "MONIO",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        } )
    }) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize()
                .background(MaterialTheme.colorScheme.onSecondary)
        ) {

            LoadAndShowInterstitial(context = context)

            val pageSate = rememberPagerState(
                pageCount = { 2 }
            )

            val continueScape = rememberCoroutineScope()

            Box(
                modifier = Modifier.fillMaxWidth().height(65.dp).padding(10.dp)
                    .background(MaterialTheme.colorScheme.onPrimary).clip(
                    RoundedCornerShape(20.dp)
                ).background(gray)
            ) {
                TabRow(
                    selectedTabIndex = pageSate.currentPage,
                    containerColor = gray,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pageSate.currentPage]),
                            color = blue // 🎯 Change indicator color here
                        )
                    }

                ) {

                    Tab(
                        selected = pageSate.currentPage == 0,
                        text = { Text("Image", color = black, fontWeight = FontWeight.Bold) },
                        onClick = {
                            continueScape.launch {
                                pageSate.animateScrollToPage(0)
                            }

                        }, modifier = Modifier
                            .background(if (pageSate.currentPage == 0) blue else Color.Transparent)

                    )

                    Tab(
                        selected = pageSate.currentPage == 1, // ✅ Use 1 for Video tab
                        text = { Text("Videos", color = black, fontWeight = FontWeight.Bold) },
                        onClick = {
                            continueScape.launch {
                                pageSate.animateScrollToPage(1)
                            }
                        }, modifier = Modifier
                            .background(if (pageSate.currentPage == 1) blue else Color.Transparent)
                    )

                }
            }

            HorizontalPager(state = pageSate, userScrollEnabled = false) { page ->
                when (page) {
                    0 -> {
                        // Image Page Design

                            Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)

                            ) {


                                items(allImage.chunked(2)) { rowItem ->

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),

                                        ) {
                                        rowItem.forEach {


                                            val uri = "https://monioapp.com/photos/${it.image_url}"
                                            val uri1 = "https://monioapp.com/photos/${it.image_url}"
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(uri)
                                                    .crossfade(true)
                                                    .build(),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .aspectRatio(1f)
                                                    .clip(RoundedCornerShape(15.dp)).clickable {
                                                        val encodedUri = Uri.encode(uri)
                                                        val encodedUri1 = Uri.encode(uri1)
                                                        GloableNavigation.navController.navigate("${Screens.ShareScreen.route}/$encodedUri/$encodedUri1")
                                                    },
                                                contentScale = ContentScale.Crop,
                                                placeholder = painterResource(R.drawable.placeholder),
                                                error = painterResource(R.drawable.placeholder)
                                            )


                                        }


                                        if (rowItem.size < 2) {

                                            Spacer(Modifier.weight(1f))
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






                    }

                    1 -> {
                        // Video Page Design

                        Box(modifier = Modifier.fillMaxSize()) {

                            // Scrollable Video List
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 60.dp), // Leave space for banner
                                contentPadding = PaddingValues(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(allVideo.chunked(2)) { rowItem ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowItem.forEach {
                                            val uri = "https://monioapp.com/photos/${it.video_url}"
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .aspectRatio(9f / 17f)
                                                    .clickable {
                                                        val encodedUri = Uri.encode(uri)
                                                        val encodedUri1 = "null"
                                                        GloableNavigation.navController.navigate("${Screens.ShareScreen.route}/$encodedUri1/$encodedUri")
                                                    },
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                ExoVideoPlayer(
                                                    videoUrl = uri,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .aspectRatio(9f / 16f)
                                                )
                                                Text("Click Preview")
                                            }
                                        }

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

                            // ✅ Single Banner Ad at bottom center
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


                    }

                }


            }


        }

    }}