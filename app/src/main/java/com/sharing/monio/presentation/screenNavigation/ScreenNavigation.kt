

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sharing.monio.presentation.screen.dark_and_light_mode.ThemeViewModel
import com.sharing.monio.presentation.screen.downloadScreen.DownloadScreen
import com.sharing.monio.presentation.screen.homeScreen.HomeScreen
import com.sharing.monio.presentation.screen.homeScreen.Photos
import com.sharing.monio.presentation.screen.loginScreen.LoginScreen
import com.sharing.monio.presentation.screen.photosScreen.PhotosScreen
import com.sharing.monio.presentation.screen.sharephotoandVideo.ShareScreen

import com.sharing.monio.presentation.screen.singupScreen.SingUpScreen
import com.sharing.monio.presentation.screen.videoscreen.VideoScreen
import com.sharing.monio.presentation.screenNavigation.Screens
import np.com.bimalkafle.bottomnavigationdemo.BottomNavigation


@Composable
fun ScreenNavigation(themeViewModel: ThemeViewModel) {
    val context = LocalContext.current
    val navController = rememberNavController()
    GloableNavigation.navController = navController



    // If email is saved, go directly to HomeScreen
    val sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", null)

    // Decide the start screen
    val startDestination = when {
        isFirstTime(context) -> Screens.WelcomeScreen.route
        email != null -> Screens.BottomScreen.route
        else -> Screens.LoginScreen.route
    }



    NavHost(navController = navController, startDestination = startDestination){

        composable (Screens.WelcomeScreen.route){

          WelcomeScreen()
        }


        composable (Screens.SingUpScreen.route){

            SingUpScreen()
        }

        composable (Screens.LoginScreen.route){

            LoginScreen()
        }

        composable (Screens.HomeScreen.route){

            HomeScreen()
        }
        composable (Screens.PhotosScreen.route){

            PhotosScreen()
        }
        composable (Screens.VideoScreen.route){

            VideoScreen()
        }

        composable (Screens.ProfileScreen.route){

            ProfileScreen(themeViewModel)
        }

        composable (Screens.Photos.route){

            Photos()
        }
        composable (Screens.DownloadScreen.route){

            DownloadScreen()
        }


        composable (Screens.ShareScreen.route + "/{image_uri}/{video_uri}"){


            val image_uri = it.arguments?.getString("image_uri")
            val video_uri = it.arguments?.getString("video_uri")
            ShareScreen(image_uri ?: "", video_uri ?: "")
        }

        composable (Screens.BottomScreen.route){

            BottomNavigation(themeViewModel = themeViewModel)
        }




    }


}

object GloableNavigation{

    lateinit var navController: NavController
}

fun isFirstTime(context: Context): Boolean {
    val prefs = context.getSharedPreferences("App", Context.MODE_PRIVATE)
    val isFirst = prefs.getBoolean("isFirstTime", true)
    if (isFirst) {
        prefs.edit().putBoolean("isFirstTime", false).apply()
    }
    return isFirst
}

fun isLoading(context: Context, emails: String){


}
