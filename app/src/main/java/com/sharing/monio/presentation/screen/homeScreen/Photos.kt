package com.sharing.monio.presentation.screen.homeScreen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.sharing.monio.R
import com.sharing.monio.data.api.PhotoModelItem
import com.sharing.monio.data.repository.RetrofitClient
import com.sharing.monio.presentation.screen.Admob.BannerAdView
import com.sharing.monio.presentation.screenNavigation.Screens
import com.sharing.monio.presentation.ui.theme.gray


import com.sharing.monio.presentation.viewModel.PhotoViewModel
import java.net.URLEncoder

data class ImageModel(
    val image: String? = null
)


@Composable
@Preview(showSystemUi = true)
fun Photos(modifier: Modifier = Modifier, viewModel: PhotoViewModel = viewModel()) {


    val photos by viewModel.categories
    val allImage by viewModel.allPhotos

    var seletectedCatgory by remember { mutableStateOf<PhotoModelItem?>(null) }

    var isClick by remember { mutableStateOf(true) }


    var search by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var searchTxt = search.text


    Column(modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onSecondary)) {




        Column (modifier = Modifier.padding(10.dp)){

            OutlinedTextField(
                value = search, onValueChange = {
                    search = it
                }, shape = RoundedCornerShape(10.dp), colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor =  MaterialTheme.colorScheme.onSurface,
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

            itemsIndexed (photos!!) { index, category ->
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

                    Text( category.category_name, fontSize = 20.sp, modifier = Modifier.clickable {
                        seletectedCatgory = category
                        isClick = false
                    })
                }
            }

        }

        Spacer(modifier.height(10.dp))



        val imageList = seletectedCatgory?.images ?: emptyList()


     if (isClick){

         Box(Modifier.fillMaxSize()){
         LazyColumn(

             contentPadding = PaddingValues(8.dp),
             verticalArrangement = Arrangement.spacedBy(8.dp)
         ) {

             val filteredImages = allImage.filter { image ->
                 image.title.contains(searchTxt, ignoreCase = true) ||
                         image.description.contains(searchTxt, ignoreCase = true) ||
                         image.language.contains(searchTxt, ignoreCase = true) ||
                         image.tags.contains(searchTxt, ignoreCase = true)
             }
             items(filteredImages.chunked(2)) { rowItem ->

                 Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.spacedBy(8.dp),

                     ) {
                     rowItem.forEach {


                         val uri = "https://monioapp.com/photos/${it.image_url}"
                         val uri1 = ""
                         AsyncImage(
                             model = ImageRequest.Builder(LocalContext.current)
                                 .data(uri)
                                 .crossfade(true)
                                 .build(),
                             contentDescription = null,
                             modifier = modifier
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

                         Spacer(modifier.weight(1f))
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

         Box(Modifier.fillMaxSize()) {
             LazyColumn(
                 contentPadding = PaddingValues(8.dp),
                 verticalArrangement = Arrangement.spacedBy(8.dp)
             ) {

                 val filteredImages = imageList.filter { image ->
                     image.title.contains(searchTxt, ignoreCase = true) ||
                             image.description.contains(searchTxt, ignoreCase = true) ||
                             image.language.contains(searchTxt, ignoreCase = true) ||
                             image.tags.contains(searchTxt, ignoreCase = true)
                 }
                 items(filteredImages.chunked(2)) { rowItem ->

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
                                 modifier = modifier
                                     .weight(1f)
                                     .aspectRatio(1f)
                                     .clip(RoundedCornerShape(15.dp)).clickable {
                                         val encodedUri = Uri.encode(uri)
                                         val encodedUri1 = Uri.encode(uri1)
                                         GloableNavigation.navController.navigate("${Screens.ShareScreen.route}/$encodedUri/$encodedUri1")
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

