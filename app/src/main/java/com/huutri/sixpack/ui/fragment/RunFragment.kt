package com.huutri.sixpack.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.huutri.sixpack.R
import com.huutri.sixpack.common.Ads.CommonAds
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.common.base.BaseFragment
import com.huutri.sixpack.common.data.DatabaseAccess
import com.huutri.sixpack.common.util.CountDownTimerWithPause
import com.huutri.sixpack.ui.activity.VideoAnimationActivity
import kotlinx.android.synthetic.main.fragment_run.*
import java.text.DecimalFormat


class RunFragment : BaseFragment() {
    companion object {
        var posRun: Int = 0      // position start of motion start of day
        var posReal: Int = 0   //position start of day

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_run, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        ivBack.setOnClickListener {
            CommonAds.countShowAdFull++
            if (CommonAds.countShowAdFull == 5) {

                if (CommonAds.mInterstitialAd.isLoaded) {
                    CommonAds.mInterstitialAd.show()
                    CommonAds.mInterstitialAd.adListener = object : AdListener() {
                        override fun onAdClosed() {
                            CommonAds().loadAdsFulL(context!!)
                            (activity as BaseActivity).onBackPressed()

                        }
                    }
                } else (activity as BaseActivity).onBackPressed()

                CommonAds.countShowAdFull = 0

            } else
                (activity as BaseActivity).onBackPressed()
        }
        iv_Video.setOnClickListener {
            VideoAnimationActivity.mPos = posRun - posReal
            startActivity(Intent(context, VideoAnimationActivity::class.java))

        }
        try {
            setUpPosition()
        } catch (ex: java.lang.Exception) { //if done all exercise
            DatabaseAccess.getInstance(context!!).updateResetALLMotionStart
            posRun = posReal
            setUpPosition()

        }
        ivbefore.setOnClickListener {
            var a: Int = posRun - 1
            if (a >= posReal) {
                posRun--
                DatabaseAccess.getInstance(context!!).updateMotionStart
                setUpPosition()
                stepProgressBar.previous()
            } else {
                //posRun= posReal
                DatabaseAccess.getInstance(context!!).updateMotionStart
            }
        }

        ivAfter.setOnClickListener {
            afterClick()
        }
        stepProgressBar.numDots = OneDayFragment.totalexercise
        stepProgressBar.currentProgressDot = posRun - posReal
    }

    private fun afterClick() {
        var b: Int = posRun + 1
        if (b <= posReal + OneDayFragment.totalexercise - 1) {
            DatabaseAccess.getInstance(context!!).updateMotion
            posRun++
            setUpPosition()
            stepProgressBar.next()
        } else {
            DatabaseAccess.getInstance(context!!).updateMotion
            DatabaseAccess.getInstance(context!!).updateDayinish
            (activity as BaseActivity).onBackPressed()

        }
    }

    private fun setUpPosition() {
        var pos = posRun - posReal
        try {
            Glide.with(context!!).load(
                context!!.resources.getIdentifier(
                    OneDayFragment.arrExercise_Move?.listMove?.get(pos)?.LINK_IMAGE_MOVE,
                    "drawable",
                    "com.huutri.sixpack"
                )
            ).into(ivRun)

        } catch (ex: Exception) {
        }
        tvTitle.text = OneDayFragment.arrExercise_Move?.listMove?.get(pos)?.NAME_MOVE
        var textTime = OneDayFragment.arrExercise_Move?.listExercise?.get(pos)?.TIME_EX
        tvTime.text = textTime
        try {
            yourCountDownTimer.cancel()

        } catch (ex: java.lang.Exception) {
        }
        if (textTime!!.startsWith("x")) {   //if motion type X time
            Glide.with(context!!).load(R.drawable.ic_check_run).into(ivFinish)
            var xMotion = textTime.substring(1, textTime.length).trim()
            if (xMotion.toInt() % 2 == 0) {
                tvEachSide.visibility = View.VISIBLE
                var divide = xMotion.toInt() / 2
                tvEachSide.text = getString(R.string.each_side) + " x" + divide

            } else {
                tvEachSide.visibility = View.GONE

            }
            tvTimeIncease.visibility = View.GONE
        } else {
            tvEachSide.visibility = View.GONE
            tvTimeIncease.visibility = View.VISIBLE
            tvTimeIncease.setOnClickListener {
                //+20s
                var isTime = yourCountDownTimer.timeLeft() + 20000
                yourCountDownTimer.cancel()
                createCountDownTimer(isTime)

            }
            var a = OneDayFragment.arrExercise_Move?.listExercise?.get(pos)?.TIME_EX!!.substring(
                3,
                OneDayFragment.arrExercise_Move?.listExercise?.get(pos)?.TIME_EX!!.length
            )
            var b = a.toInt() * 1000
            if (b == 0) b = 60000  //---hard data default 1 minute

            createCountDownTimer(b.toLong())


        }
        btnFinishs.setOnClickListener {
            if (yourCountDownTimer.isPaused) {
                yourCountDownTimer.resume()
                Glide.with(context!!).load(R.drawable.ic_resume_run).into(ivFinish)
            } else {
                yourCountDownTimer.pause()
                Glide.with(context!!).load(R.drawable.ic_pause_run).into(ivFinish)

            }
        }
    }

    private fun createCountDownTimer(time: Long) {
        yourCountDownTimer =
            object : CountDownTimerWithPause(
                time.toLong(),
                1000
                , true
            ) {                     //geriye sayma

                override fun onTick(millisUntilFinished: Long) {

                    val f = DecimalFormat("00")
//                    val hour = millisUntilFinished / 3600000 % 24
//                    val min = millisUntilFinished / 60000 % 60
//                    val sec = millisUntilFinished / 1000 % 60
//
//                    cMeter.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec))
                    tvTime.setText("00:" + f.format(millisUntilFinished / 1000))
                    Glide.with(context!!).load(R.drawable.ic_resume_run).into(ivFinish)
                }

                override fun onFinish() {
                    //   cMeter.setText("00:00:00")
                    tvTime.setText("00:00")
                    Handler().postDelayed({
                        afterClick()
                    }, 2000)
                }
            }

        yourCountDownTimer.create()
    }

    lateinit var yourCountDownTimer: CountDownTimerWithPause
    override fun onPause() {
        yourCountDownTimer.pause()
        Glide.with(context!!).load(R.drawable.ic_pause_run).into(ivFinish)
        super.onPause()
    }

    //
    override fun onResume() {
        try {
            yourCountDownTimer.resume()
            Glide.with(context!!).load(R.drawable.ic_resume_run).into(ivFinish)
        } catch (ex: java.lang.Exception) {
        }
        super.onResume()

    }

}


