import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sharing.monio.presentation.screen.Admob.BannerAdView
import com.sharing.monio.presentation.screen.Admob.NativeAdView
import com.sharing.monio.presentation.screen.dark_and_light_mode.ThemeViewModel
import com.sharing.monio.presentation.screenNavigation.Screens
import com.sharing.monio.presentation.ui.theme.black
import com.sharing.monio.presentation.ui.theme.gray
import com.sharing.monio.presentation.ui.theme.gray_dark


@Composable
fun ProfileScreen(themeViewModel: ThemeViewModel) {

    val context = LocalContext.current

    var mode by remember {
        mutableStateOf(themeViewModel.isDarkTheme.value)
    }

    val versionName = remember {
        context.packageManager
            .getPackageInfo(context.packageName, 0).versionName
    }
    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 15.dp)
                .background(MaterialTheme.colorScheme.onSecondary)


        ) {

            Row(Modifier.padding(10.dp)) {

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        "Profile",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                Box(Modifier.fillMaxSize()) {
                    Switch(
                        checked = mode,
                        onCheckedChange = {
                            mode = it
                            themeViewModel.toggleTheme() // Toggle only once per interaction
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.onSurface
                        ), modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
            }
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.onSecondary),


        ) {
            val sharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)
            val email = sharedPreferences.getString("email",null)

            val items = listOf(
                ItemsModel("Email: $email"),
                ItemsModel("Privacy Policy"),
                ItemsModel("Terms & Conditions"),
                ItemsModel("App Version : $versionName"),
               // ItemsModel("Download Our More App"),
            )

            LazyColumn (contentPadding = PaddingValues(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){


                itemsIndexed  (items){ index, item->

                    Column (modifier = Modifier.fillMaxWidth().height(50.dp).clip(
                        RoundedCornerShape(10.dp)).background(gray_dark).clickable{
                        if (index == 1) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wableearnmoneydaily.blogspot.com/2025/06/privacy-policy-for-monio.html"))
                            context.startActivity(intent)
                        } else if (index == 2) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wableearnmoneydaily.blogspot.com/2025/06/terms-and-conditions-for-monio.html"))
                            context.startActivity(intent)
                        }

                    },
                        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                        Text( item.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 15.dp), color = black)
                    }
                }
                item {
                    NativeAdView(context)
                }
                item {
                    BannerAdView(context)
                }
            }

        }
    }
}



data class ItemsModel(
    val title : String = ""
)
