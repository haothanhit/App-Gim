package com.huutri.sixpack.ui.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.huutri.sixpack.R
import com.huutri.sixpack.common.application.MyApplication
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.common.base.BaseFragment
import com.huutri.sixpack.ui.activity.SplashActivity
import kotlinx.android.synthetic.main.fragment_me.*


class MeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        //ads
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        btnRateUs.setOnClickListener {
            (activity as BaseActivity).openMyApp()
        }
        btnShareWith.setOnClickListener {
            (activity as BaseActivity).share()
        }
        btnFeeBack.setOnClickListener {
            (activity as BaseActivity).feedback()
        }
        btnDeleteAllData.setOnClickListener {
            (activity as BaseActivity).deleteAllData()
        }

    }
}
