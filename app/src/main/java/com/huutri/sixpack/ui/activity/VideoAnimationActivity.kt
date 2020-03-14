package com.huutri.sixpack.ui.activity

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.huutri.sixpack.R
import com.huutri.sixpack.ui.fragment.OneDayFragment
import kotlinx.android.synthetic.main.activity_video_animation.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class VideoAnimationActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    var keyYoutube = "AIzaSyC-z8iQpW_wyuj_ZHRr3QpBbK7B-yWCRb0"

    companion object {
        var mPos: Int = 0
    }

    private var mplayer: YouTubePlayer? = null

    private var isAnimation: Boolean = false
    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if (!wasRestored) {
            player?.loadVideo(
                getIDYoutube(
                    OneDayFragment.arrExercise_Move?.listMove?.get(
                        mPos!!
                    )?.LINK_VIDEO_MOVE!!.trim()
                ), 0
            )
        }
        mplayer=player
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_animation)
        youtube_player.initialize(keyYoutube, this)
        initView()
    }


    private fun initView() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        tvTitleMotion.text =
            OneDayFragment.arrExercise_Move?.listMove?.get(mPos!!)?.NAME_MOVE
        tvDetailMotion.text =
            OneDayFragment.arrExercise_Move?.listMove?.get(mPos!!)
                ?.DESCRIPTION_MOVE



        Glide.with(this!!).load(
            this!!.resources.getIdentifier(
                OneDayFragment.arrExercise_Move?.listMove?.get(mPos!!)?.LINK_IMAGE_MOVE!!,
                "drawable",
                "com.huutri.sixpack"
            )
        ).into(ivAnimation)
        llTitle.setOnClickListener {
            if (!isAnimation) {
                tvTitle.text = getString(R.string.animation)
                Glide.with(this!!).load(R.drawable.ic_animation).into(ivTitle)
                youtube_player.visibility = View.GONE
                mplayer?.pause()
                ivAnimation.visibility = View.VISIBLE
                isAnimation = true
        //        adView.visibility=View.VISIBLE

            } else {
                tvTitle.text = getString(R.string.video)
                Glide.with(this!!).load(R.drawable.ic_video).into(ivTitle)
                youtube_player.visibility = View.VISIBLE
                ivAnimation.visibility = View.GONE
                isAnimation = false
                mplayer?.play()
           //     adView.visibility=View.INVISIBLE

            }


        }
//ads
    //    val adRequest = AdRequest.Builder().build()
       // adView.loadAd(adRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        mplayer?.release()
    }

    private fun getIDYoutube(url: String): String {
//        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
//
//        val compiledPattern = Pattern.compile(pattern)
//        val matcher = compiledPattern.matcher(url)
//
//        if (matcher.find()) {
//            return matcher.group()
//        }
        var idUrl = url.substring(url.lastIndexOf('/') + 1, url.length)
        return idUrl
    }
}
