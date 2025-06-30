package com.sharing.monio.presentation.screen.singupScreen


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sharing.monio.R
import com.sharing.monio.presentation.screenNavigation.Screens
import com.sharing.monio.presentation.ui.theme.black
import com.sharing.monio.presentation.ui.theme.gray
import com.sharing.monio.presentation.ui.theme.white
import com.sharing.monio.presentation.viewModel.SignUpViewmodel

@Composable
fun SingUpScreen() {

    val viewModel : SignUpViewmodel = viewModel()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current


    Scaffold {

        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(white),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(painter = painterResource(R.drawable.flower), "", Modifier.size(70.dp))
            Spacer(Modifier.height(30.dp))
            Text(
                "Sign In To MONIO",
                color = black,
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.roboto))
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Daily inspiration to keep you going!",
                color = black,
                fontSize = 16.sp,

                )

            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    "Email Address",
                    color = black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 5.dp)
                )

                OutlinedTextField(
                    value = email, onValueChange = {
                        email = it
                    }, shape = RoundedCornerShape(10.dp), colors = TextFieldDefaults.colors(
                        focusedTextColor = black,
                        unfocusedTextColor = black,
                        unfocusedIndicatorColor = white,
                        focusedContainerColor = gray,
                        unfocusedContainerColor = gray,
                    ), leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.email),
                            "",
                            tint = black,
                            modifier = Modifier.size(20.dp)
                        )
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )

                ///passwords
                Spacer(Modifier.height(20.dp))

                var isVisible by remember { mutableStateOf(false) }

                var icon = if (isVisible)
                    painterResource(R.drawable.eye1)
                else
                    painterResource(R.drawable.eye2)


                Text(
                    "Password",
                    color = black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 5.dp)
                )

                OutlinedTextField(
                    value = password, onValueChange = {
                        password = it
                    },shape = RoundedCornerShape(10.dp), colors = TextFieldDefaults.colors(
                        focusedTextColor = black,
                        unfocusedTextColor = black,
                        unfocusedIndicatorColor = white,
                        focusedContainerColor = gray,
                        unfocusedContainerColor = gray,
                    ), leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.password),
                            "",
                            tint = black,
                            modifier = Modifier.size(20.dp)
                        )
                    }, trailingIcon = {
                         IconButton(onClick = {
                             isVisible = !isVisible
                         }) {
                        Icon(
                            painter = icon,
                            "",
                            tint = black,
                            modifier = Modifier.size(20.dp))


                         }


                    }, visualTransformation = if (isVisible) VisualTransformation.None
                    else PasswordVisualTransformation(), modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )

                //---------------------------
                Spacer(Modifier.height(30.dp))
                Button(onClick = {

                    if (email.isNotEmpty() && password.isNotEmpty()){
                        viewModel.signup(context,email,password)


                    }else{
                        Toast.makeText(context, "Fill All", Toast.LENGTH_SHORT).show()
                    }
                }, Modifier
                    .fillMaxWidth()
                    .height(50.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = black
                ), shape = RoundedCornerShape(8.dp)
                ) {

                    val isloading =viewModel.isLoading
                    Text(if (isloading) "Creating.." else "Sign In", color = white)

                    viewModel.singupMsg?.let {
                        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
                    }

                    Spacer(Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.ArrowForward,"", tint = white, modifier = Modifier.size(15.dp))
                }

                Spacer(Modifier.height(20.dp))

                //google login

                    GoogleSignInButton(
                        context = context,
                        onSignInSuccess = { user ->
                            Toast.makeText(context, "Welcome ${user.displayName}", Toast.LENGTH_SHORT).show()
                            SaveUserEmail(context,email)
                          viewModel.signup(context, user.email.toString(),null)
                            GloableNavigation.navController.navigate(Screens.BottomScreen.route)
                            // Navigate to Home screen here
                        },
                        onSignInError = { error ->
                           // Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

                        }
                    )


                //google login

                Spacer(Modifier.height(20.dp))
                Row (Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
                    Text(
                        "Already have an Account?",
                        fontSize = 14.sp
                    )
                    Text(
                        "Login",
                        color = black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                        ,modifier= Modifier.clickable{
                            GloableNavigation.navController.navigate(Screens.LoginScreen.route)
                        }
                    )
                }



                Spacer(Modifier.height(70.dp))
            }


        }
    }

}

fun SaveUserEmail(context: Context,email: String){

    val sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)

    sharedPreferences.edit().putString("email",email).apply()

}