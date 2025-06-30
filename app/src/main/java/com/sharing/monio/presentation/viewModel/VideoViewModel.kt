package com.sharing.monio.presentation.viewModel



import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharing.monio.data.api.Image
import com.sharing.monio.data.api.PhotoCatagorys
import com.sharing.monio.data.api.PhotoModel
import com.sharing.monio.data.api.PhotoModelItem
import com.sharing.monio.data.api.VideoItem
import com.sharing.monio.data.api.VideoX
import com.sharing.monio.data.repository.RetrofitClient

import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val _categories = mutableStateOf<List<VideoItem>>(emptyList())
    val categories: State<List<VideoItem>> = _categories


    private val _allVideo = mutableStateOf<List<VideoX>>(emptyList())
    val allVideo: State<List<VideoX>> = _allVideo



    init {

        FetchVideo()
    }

    init {

        FetchAllVideo()
    }


    fun FetchVideo() {

        viewModelScope.launch {
            try {

                val response = RetrofitClient.instance.getVideoFetch()
                _categories.value = response

            } catch (e: Exception) {


            }
        }
    }


    fun FetchAllVideo() {

        viewModelScope.launch {
            try {

                val response = RetrofitClient.instance.getAllVideoFetch()
                _allVideo.value = response

            } catch (e: Exception) {


            }
        }
    }



}