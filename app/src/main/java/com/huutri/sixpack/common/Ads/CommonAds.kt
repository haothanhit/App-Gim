package com.huutri.sixpack.common.Ads

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.huutri.sixpack.R

class CommonAds {
    companion object{
        lateinit var mInterstitialAd: InterstitialAd
        var countShowAdFull=0
    }
    public fun loadAdsFulL(context: Context){
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = context.getString(R.string.ad_full_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
}