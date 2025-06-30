package com.sharing.monio.presentation.screen.singupScreen

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sharing.monio.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.sharing.monio.presentation.ui.theme.blue
import com.sharing.monio.presentation.viewModel.SignUpViewmodel


@Composable
fun GoogleSignInButton(
    context: Context,
    onSignInSuccess: (FirebaseUser) -> Unit,
    onSignInError: (String) -> Unit
) {


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        onSignInSuccess(authResult.result?.user!!)
                    } else {
                        onSignInError("Firebase sign-in failed")
                    }
                }
        } catch (e: ApiException) {
            onSignInError("Google sign-in failed: ${e.message}")
        }
    }

    Button(onClick = {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // from google-services.json
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(context, options)
        launcher.launch(client.signInIntent)
    }, Modifier.fillMaxWidth().height(45.dp), colors = ButtonDefaults.buttonColors(
        containerColor = blue
    ), shape = RoundedCornerShape(6.dp),) {
        Image(painter = painterResource(R.drawable.google),"", Modifier.size(25.dp))
    }
}
