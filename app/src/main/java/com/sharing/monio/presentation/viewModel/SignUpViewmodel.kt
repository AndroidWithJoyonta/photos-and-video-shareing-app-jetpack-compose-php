package com.sharing.monio.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharing.monio.data.repository.RetrofitClient
import com.sharing.monio.presentation.screenNavigation.Screens
import kotlinx.coroutines.launch

class SignUpViewmodel : ViewModel() {



     var isLoading by  mutableStateOf(false)
         private set

    var singupMsg by mutableStateOf<String?>(null)
        private set

    fun signup(context: Context,email: String, password: String?){

        isLoading = true
        singupMsg = null

        viewModelScope.launch {

            try {
                val response = RetrofitClient.instance.insertUser(email, password.toString())
                if (response.isSuccessful && response.body()?.success == true) {
                    isLoading = false
                    singupMsg = "SignUp Success"
                    SaveUserEmail(context,email)
                    GloableNavigation.navController.navigate(Screens.BottomScreen.route)
                    Log.d("Insert", "Success: ${response.body()?.message}")
                } else {
                    isLoading = false
                    singupMsg = response.body()?.message ?: "Signup failed"
                    Log.e("Insert", "Error: ${response.body()?.message}")
                    SaveUserEmail(context,email)
                }
            } catch (e: Exception) {
                isLoading = false
                singupMsg = "Network error: ${e.localizedMessage}"
                Log.e("Insert", "Exception: ${e.message}")
            }

        }}

    fun loagin(context : Context,email: String, password: String){

        isLoading = true
        singupMsg = null

        viewModelScope.launch {

            try {
                val response = RetrofitClient.instance.login(email, password)
                if (response.isSuccessful && response.body()?.success == true) {
                    isLoading = false
                    singupMsg = "Login Success"
                    //SaveUserEmail(context,email)
                    GloableNavigation.navController.navigate(Screens.BottomScreen.route)
                    Log.d("Insert", "Success: ${response.body()?.message}")
                } else {
                    isLoading = false
                    singupMsg = response.body()?.message ?: "Signup failed"
                    Log.e("Insert", "Error: ${response.body()?.message}")
                }
            } catch (e: Exception) {
                isLoading = false
                singupMsg = "Network error: ${e.localizedMessage}"
                Log.e("Insert", "Exception: ${e.message}")
            }

        }}

    fun download_count(context: Context){

        isLoading = true
        singupMsg = null

        viewModelScope.launch {

            try {
                val response = RetrofitClient.instance.count(1)
                if (response.isSuccessful && response.body()?.success == true) {
                    isLoading = false
                    singupMsg = "SignUp Success"
                    Log.d("Insert", "Success: ${response.body()?.message}")
                } else {
                    isLoading = false
                    singupMsg = response.body()?.message ?: "Signup failed"

                }
            } catch (e: Exception) {
                isLoading = false
                singupMsg = "Network error: ${e.localizedMessage}"
                Log.e("Insert", "Exception: ${e.message}")
            }

        }}


    fun SaveUserEmail(context: Context,email: String){

        val sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)

        sharedPreferences.edit().putString("email",email).apply()

    }
}