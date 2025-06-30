package com.sharing.monio.presentation.screenNavigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home")
    object ProfileScreen : Screens("profile")
    object WelcomeScreen : Screens("welcome")
    object SingUpScreen : Screens("singup")
    object LoginScreen : Screens("login")
    object ConfirmScreen : Screens("confitm")
    object HistoryScreen : Screens("history")
    object PhotosScreen : Screens("photos")
    object Photos : Screens("photo")
    object Videos : Screens("videos")
    object ShareScreen : Screens("share")
    object DownloadScreen : Screens("download")
    object VideoScreen : Screens("video")
    object BottomScreen : Screens("bottom")
    object AppEntryPoints : Screens("point")
}