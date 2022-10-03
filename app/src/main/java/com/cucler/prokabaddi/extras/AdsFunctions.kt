package com.cucler.prokabaddi.extras

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

private var mInterstitialAd: InterstitialAd? = null
private var mVideoInterstitialAd: InterstitialAd? = null

fun loadBannerAd(context: Context, adView: AdView) {
    MobileAds.initialize(context) {}
    val mAdView: AdView = adView
    val adRequest = AdRequest.Builder().build()
    mAdView.loadAd(adRequest)
}

fun showInterstitial(context: Activity) {
    if (mInterstitialAd != null) {
        mInterstitialAd?.show(context)
    } else {
        Log.d("TAG", "The interstitial ad wasn't ready yet.")
    }
}

fun loadInterestitial(context: Context, adId: String) {
    val adRequest = AdRequest.Builder().build()

    InterstitialAd.load(context, adId, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            mInterstitialAd = null
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            mInterstitialAd = interstitialAd
        }
    })
}

fun showVideoInterstitial(activity: Activity) {
    if (mVideoInterstitialAd != null) {
        mVideoInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
            }
        }
        mVideoInterstitialAd!!.show(activity)
    }
}

fun loadVideoInterestitial(context: Context, adId: String) {
    var adRequest = AdRequest.Builder().build()

    InterstitialAd.load(
        context,
        adId,
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mVideoInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mVideoInterstitialAd = interstitialAd
            }
        })
}