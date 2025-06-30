import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.sharing.monio.R
import com.sharing.monio.presentation.screenNavigation.Screens
import com.sharing.monio.presentation.ui.theme.black
import com.sharing.monio.presentation.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {

    var content = LocalContext.current


    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(R.drawable.bg),
                "",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier.fillMaxSize()
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Welcome to MONIO",
                    color = white,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    "Join 10,000+ image and video quotes for daily inspiration",

                    color = white,
                    fontSize = 14.sp
                )
                Text(
                    "and global learning!",

                    color = white,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { GloableNavigation.navController.navigate(Screens.SingUpScreen.route) },
                    Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = white
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Create an account", color = black)
                }
                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        "Already have an Account?",
                        color = white,
                        fontSize = 14.sp
                    )
                    Text(
                        "Login",
                        color = white,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline, modifier = Modifier.clickable {
                            GloableNavigation.navController.navigate(Screens.LoginScreen.route)
                        }
                    )
                }

                Spacer(Modifier.height(70.dp))
            }
        }
    }


}