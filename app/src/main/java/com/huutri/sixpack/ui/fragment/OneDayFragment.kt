package com.huutri.sixpack.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.huutri.sixpack.R
import com.huutri.sixpack.common.Ads.CommonAds
import com.huutri.sixpack.common.Ads.CommonAds.Companion.countShowAdFull
import com.huutri.sixpack.common.Ads.CommonAds.Companion.mInterstitialAd
import com.huutri.sixpack.common.adapter.DayAdapter
import com.huutri.sixpack.common.adapter.OneDayAdapter
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.common.base.BaseFragment
import com.huutri.sixpack.common.data.DatabaseAccess
import com.huutri.sixpack.common.model.Exercise_Move
import com.huutri.sixpack.ui.activity.VideoAnimationActivity
import kotlinx.android.synthetic.main.fragment_one_day.*


class OneDayFragment : BaseFragment() {
    companion object {
        var oneDayToGetData: Int = 0
        var totalexercise = 0
        var nameDay: String? = ""
        var arrExercise_Move: Exercise_Move? = null
        var statusDay = 0

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one_day, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        arrExercise_Move = DatabaseAccess.getInstance(context!!).getOneDay
        rcvOneDay.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rcvOneDay.adapter = OneDayAdapter(
            { pos: Int -> partItemClicked(pos) })
        rcvOneDay.setHasFixedSize(true)
        val adRequest = AdRequest.Builder().build()        //ads
        adView.loadAd(adRequest)
        rcvOneDay.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0) { //crolled Upwards
                    adView.visibility = View.VISIBLE
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        tvTitle.text = nameDay
        ivBack.setOnClickListener {


            countShowAdFull++
            if (countShowAdFull == 5) {

                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                    mInterstitialAd.adListener = object : AdListener() {
                        override fun onAdClosed() {
                            CommonAds().loadAdsFulL(context!!)
                            (activity as BaseActivity).onBackPressed()

                        }
                    }
                } else (activity as BaseActivity).onBackPressed()

                countShowAdFull = 0

            } else
                (activity as BaseActivity).onBackPressed()
        }
        btnStart.setOnClickListener {
            RunFragment.posRun = DatabaseAccess.getInstance(context!!).getNumberMotionStartOfDay
            RunFragment.posReal = DatabaseAccess.getInstance(context!!).getNumberStartOfDay
            DatabaseAccess.getInstance(context!!)
                .updateDayInprogress  //update status inprogress of day
            (activity as BaseActivity).replaceFragment(
                R.id.container,
                RunFragment(),
                false
            )
        }
        if (statusDay == 2) { //finish
            btnStart.text = getString(R.string.do_it_again)
        }
        if (statusDay == 3) { //inprogress
            btnStart.visibility = View.GONE
            llProgress.visibility = View.VISIBLE
            btnReStart.setOnClickListener {
                DatabaseAccess.getInstance(context!!).updateResetALLMotionStart
                RunFragment.posReal = DatabaseAccess.getInstance(context!!).getNumberStartOfDay
                RunFragment.posRun = RunFragment.posReal
                DatabaseAccess.getInstance(context!!)
                    .updateDayInprogress  //update status inprogress of day
                (activity as BaseActivity).replaceFragment(
                    R.id.container,
                    RunFragment(),
                    false
                )
            }
            btnContinue.setOnClickListener {
                RunFragment.posRun = DatabaseAccess.getInstance(context!!).getNumberMotionStartOfDay
                RunFragment.posReal = DatabaseAccess.getInstance(context!!).getNumberStartOfDay
                DatabaseAccess.getInstance(context!!)
                    .updateDayInprogress  //update status inprogress of day
                (activity as BaseActivity).replaceFragment(
                    R.id.container,
                    RunFragment(),
                    false
                )
            }
            DayAdapter.daygetCountExerciseFinish = oneDayToGetData
            var countExerciseFinish = DatabaseAccess.getInstance(context!!)
                .countExerciseFinish  //get total exercise finish
            var percentComplete = ((countExerciseFinish.toDouble() / totalexercise!!) * 100).toInt()
            tvPercentComplete.text =
                percentComplete.toString() + "% " + context!!.getString(R.string.completed)

        }
        tvTotalExercise.text = "(" + totalexercise + ")"
    }

    var dialog: Dialog? = null  //show dialog
    private fun partItemClicked(pos: Int) {
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
        else {
            dialog = Dialog(context!!)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(true)
            dialog!!.setContentView(R.layout.dialog_detail_motion)
            val tvTitleDialog = dialog!!.findViewById(R.id.tvTitleDialog) as TextView
            tvTitleDialog.text = arrExercise_Move?.listMove?.get(pos)?.NAME_MOVE
            val tvTDetailialog = dialog!!.findViewById(R.id.tvTDetailialog) as TextView
            tvTDetailialog.text = arrExercise_Move?.listMove?.get(pos)?.DESCRIPTION_MOVE
            val tvCloseDialog = dialog!!.findViewById(R.id.tvCloseDialog) as Button

            tvCloseDialog.setOnClickListener {
                dialog!!.dismiss()
            }
            val ivDialog = dialog!!.findViewById(R.id.ivDialog) as ImageView
            try {
                Glide.with(context!!).load(
                    context!!.resources.getIdentifier(
                        arrExercise_Move?.listMove?.get(pos)?.LINK_IMAGE_MOVE,
                        "drawable",
                        "com.huutri.sixpack"
                    )
                ).into(ivDialog)

            } catch (ex: Exception) {
                Glide.with(context!!).load(R.drawable.ic_dead_bug).into(ivDialog)
            }
            val iv_Video = dialog!!.findViewById(R.id.iv_Video) as ImageView

            iv_Video.setOnClickListener {
                try {
//                startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse(exercise.listMove?.get(pos)?.LINK_VIDEO_MOVE)
//                    )
//                )
                    // val videoAnimationFragment = VideoAnimationFragment.newInstance(exercise, pos)
//                VideoAnimationFragment.mExercise = exercise
                    countShowAdFull++
                    if (countShowAdFull == 5) {

                        if (mInterstitialAd.isLoaded) {
                            mInterstitialAd.show()
                            mInterstitialAd.adListener = object : AdListener() {
                                override fun onAdClosed() {
                                    nextVideoAnimation(pos)
                                    CommonAds().loadAdsFulL(context!!)

                                }
                            }
                        } else {
                            nextVideoAnimation(pos)
                        }
                        countShowAdFull = 0

                    } else {
                        nextVideoAnimation(pos)
                    }


                } catch (ex: Exception) {
                }

            }
            dialog!!.show()
        }


    }

    private fun nextVideoAnimation(pos: Int) {
        VideoAnimationActivity.mPos = pos
        startActivity(Intent(context, VideoAnimationActivity::class.java))


        dialog!!.dismiss()
    }


}