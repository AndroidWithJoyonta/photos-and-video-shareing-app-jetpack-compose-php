package com.sharing.monio.presentation.screen.Admob




import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.AdLoader
import com.sharing.monio.R


@Composable
fun BannerAdView(context: Context) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = {
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = context.getString(R.string.banner_id) // Replace ID with your own
            adView.loadAd(AdRequest.Builder().build())
            adView
        }
    )
}

fun LoadAndShowInterstitial(context: Context) {
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
        context,
        context.getString(R.string.interstitial_id), // Test Ad ID
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                val activity = context.findActivity()
                activity?.let {
                    ad.show(it)
                }
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdError", adError.toString())
            }
        }
    )
}
@Composable
fun NativeAdView(context: Context) {
    AndroidView(
        modifier = Modifier,
        factory = {
            val inflater = LayoutInflater.from(context)
            val adView = inflater.inflate(R.layout.native_ad_layout, null) as NativeAdView
            val adLoader = com.google.android.gms.ads.AdLoader.Builder(
                context,
                context.getString(R.string.native_id) // Your AdMob Native Ad Unit ID
            )
                .forNativeAd { nativeAd ->
                    val headlineView = adView.findViewById<TextView>(R.id.native_ad_headline)
                    headlineView.text = nativeAd.headline
                    adView.headlineView = headlineView
                    adView.setNativeAd(nativeAd)
                }
                .withAdListener(object : com.google.android.gms.ads.AdListener() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        Log.e("NativeAd", "Failed to load: $error")
                    }
                })
                .build()

            adLoader.loadAd(AdRequest.Builder().build())
            adView
        }
    )
}




fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}