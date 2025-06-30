package com.sharing.monio.presentation.screen.videoscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharing.monio.presentation.screen.homeScreen.Photos
import com.sharing.monio.presentation.screen.homeScreen.Videos
import com.sharing.monio.presentation.ui.theme.black
import com.sharing.monio.presentation.ui.theme.gray
import com.sharing.monio.presentation.ui.theme.white


@Composable
@Preview(showSystemUi = true)
fun VideoScreen() {


    Scaffold (bottomBar = {

    }){
        Column (modifier = Modifier.padding(it).background(MaterialTheme.colorScheme.onSecondary)){


                Videos()

        }
    }

}