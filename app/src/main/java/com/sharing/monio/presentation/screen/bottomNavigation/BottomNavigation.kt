package np.com.bimalkafle.bottomnavigationdemo

import ProfileScreen
import com.sharing.monio.presentation.screen.dark_and_light_mode.ThemeViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sharing.monio.R
import com.sharing.monio.presentation.screen.Admob.BannerAdView
import com.sharing.monio.presentation.screen.Admob.LoadAndShowInterstitial
import com.sharing.monio.presentation.screen.downloadScreen.DownloadScreen
import com.sharing.monio.presentation.screen.homeScreen.HomeScreen
import com.sharing.monio.presentation.screen.photosScreen.PhotosScreen

import com.sharing.monio.presentation.screen.videoscreen.VideoScreen
import com.sharing.monio.presentation.ui.theme.blue
import com.sharing.monio.presentation.ui.theme.gray


data class NavItem(
    val label : String,
    val icon : Int,
    val badgeCount : Int,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(modifier: Modifier = Modifier,themeViewModel: ThemeViewModel) {


    val navItemList = listOf(
        NavItem("Home", R.drawable.home,0),
        NavItem("Notification", R.drawable.photo,0),
        NavItem("Settings", R.drawable.videos,0),
        NavItem("Settings", R.drawable.downloads,0),
        NavItem("Settings", R.drawable.setting,0)
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onSecondary),


        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected =  selectedIndex == index ,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            BadgedBox(badge = {
                                if(navItem.badgeCount>0)
                                    Badge(){
                                        Text(text = navItem.badgeCount.toString())
                                    }
                            }) {
                                Icon(painter = painterResource(navItem.icon) , contentDescription = "Icon",modifier= Modifier.size(22.dp))
                            }

                        },
                        label = {
                            //Text(text = navItem.label)
                        }, colors = NavigationBarItemDefaults.colors(

                            unselectedIconColor = Color.Gray,
                            indicatorColor = blue,


                        ),
                        )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex,themeViewModel)
    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, themeViewModel: ThemeViewModel) {

    when(selectedIndex){
        0-> {HomeScreen()
            LoadAndShowInterstitial(LocalContext.current)
        }
        1-> {PhotosScreen()
            LoadAndShowInterstitial(LocalContext.current)}
        2-> {VideoScreen()
            LoadAndShowInterstitial(LocalContext.current)}
        3->{ DownloadScreen()
            LoadAndShowInterstitial(LocalContext.current)}
        4-> {ProfileScreen(themeViewModel)
            LoadAndShowInterstitial(LocalContext.current)}
    }
}


















