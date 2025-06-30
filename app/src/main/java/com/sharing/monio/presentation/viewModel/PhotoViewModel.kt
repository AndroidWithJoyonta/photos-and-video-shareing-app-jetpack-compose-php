package com.sharing.monio.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharing.monio.data.api.Image
import com.sharing.monio.data.api.PhotoCatagorys
import com.sharing.monio.data.api.PhotoModel
import com.sharing.monio.data.api.PhotoModelItem
import com.sharing.monio.data.repository.RetrofitClient
import kotlinx.coroutines.launch

class PhotoViewModel : ViewModel() {

    private val _categories = mutableStateOf<List<PhotoModelItem>>(emptyList())
    val categories: State<List<PhotoModelItem>> = _categories


    private val _allPhotos = mutableStateOf<List<Image>>(emptyList())
    val allPhotos: State<List<Image>> = _allPhotos



    init {

        FetchPhoto()
    }

    init {

        FetchAllPhoto()
    }


    fun FetchPhoto() {

        viewModelScope.launch {
            try {

                val response = RetrofitClient.instance.getPhotoFetch()
                _categories.value = response

            } catch (e: Exception) {


            }
        }
    }


    fun FetchAllPhoto() {

        viewModelScope.launch {
            try {

                val response = RetrofitClient.instance.getAllPhotoFetch()
                _allPhotos.value = response

            } catch (e: Exception) {


            }
        }
    }



}