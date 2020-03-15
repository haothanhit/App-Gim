package com.huutri.sixpack.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.huutri.sixpack.R
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.common.base.BaseFragment
import com.huutri.sixpack.common.data.DatabaseAccess
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.fragment_rest.*
import kotlinx.android.synthetic.main.fragment_rest.adView

class RestFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rest, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        //ads
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adView.visibility = View.VISIBLE
            }
        }

        ivBack.setOnClickListener {
            (activity as BaseActivity).onBackPressed()
        }
        btnFinishs.setOnClickListener {
            DatabaseAccess.getInstance(context!!).updateDayinish
            (activity as BaseActivity).onBackPressed()

        }
    }




}
