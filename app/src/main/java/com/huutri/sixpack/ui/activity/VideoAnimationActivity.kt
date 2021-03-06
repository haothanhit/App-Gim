package com.huutri.sixpack.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.firebase.database.*
import com.huutri.sixpack.R
import com.huutri.sixpack.common.firebase.realtime.CommonDatabase
import com.huutri.sixpack.ui.fragment.OneDayFragment
import kotlinx.android.synthetic.main.activity_video_animation.*
import java.lang.Exception


class VideoAnimationActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    var keyYoutube = "AIzaSyDF8Gb59Fu7SacGYfFSqrt8Dc-aSebqz1o"

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
        mFirebaseInstance=CommonDatabase.mfirebaseDatabase
        if(mFirebaseInstance==null)mFirebaseInstance= FirebaseDatabase.getInstance()
        // get reference to 'AllVideo' node
        mFirebaseDatabase = mFirebaseInstance?.getReference("AllVideo")?.child( OneDayFragment.arrExercise_Move?.listMove?.get(mPos!!)?.LINK_IMAGE_MOVE!!.trim())
        mFirebaseDatabase?.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

                Toast.makeText(applicationContext, "Link error", Toast.LENGTH_LONG).show()

            }

            override fun onDataChange(p0: DataSnapshot) { // if have link
                try {
                    if (!wasRestored) {                // run video
                        player?.loadVideo(
                            getIDYoutube(p0.getValue() as String), 0
                        )
                    }
                    mplayer=player
                }catch (ex:Exception){}

            }


        })




    }
    private var mFirebaseInstance: FirebaseDatabase? = null
    private var mFirebaseDatabase: DatabaseReference? = null

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
