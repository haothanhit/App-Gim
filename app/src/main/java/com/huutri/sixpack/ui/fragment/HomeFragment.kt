package com.huutri.sixpack.ui.fragment

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.huutri.photoeditor.common.adapter.ViewPagerAdapter
import com.huutri.sixpack.R
import com.huutri.sixpack.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {
    var adapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setupViewPager()
        ivPresent.setOnClickListener {
            if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
            else {
                dialog = Dialog(context!!, R.style.Theme_Design_Light_NoActionBar)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.setCancelable(true)
                dialog!!.setCanceledOnTouchOutside(false)
                dialog!!.setContentView(R.layout.dialog_ads_home)
//                val window = dialog?.getWindow()
//                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                dialog!!.getWindow()!!.setBackgroundDrawableResource(R.color.transparent3)
                val ivReward = dialog!!.findViewById(R.id.ivReward) as ImageView
                Glide.with(context!!).load(R.drawable.bg_reward).into(ivReward)
                val ivClosePresent = dialog!!.findViewById(R.id.ivClosePresent) as FrameLayout
                ivClosePresent.setOnClickListener { dialog!!.dismiss() }
                //ads
                val template = dialog!!.findViewById(R.id.my_template) as TemplateView
                var adLoader = AdLoader.Builder(context!!, getString(R.string.ad_native_id))
                    .forUnifiedNativeAd(object : UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
                        override fun onUnifiedNativeAdLoaded(unifiedNativeAd: UnifiedNativeAd) {
                            try {
                                val styles = NativeTemplateStyle.Builder().withMainBackgroundColor(
                                    context!!.getDrawable(R.color.white) as ColorDrawable?
                                ).build()

                                template.setStyles(styles)
                                template.setNativeAd(unifiedNativeAd)
                            } catch (ex: Exception) {
                            }

                        }
                    }).build()
                adLoader.loadAd(AdRequest.Builder().build())

                dialog!!.show()
            }

        }

    }

    var dialog: Dialog? = null  //show dialog

    private fun setupViewPager() {
        adapter = ViewPagerAdapter(childFragmentManager!!) //viewpager
        viewPager.adapter = adapter
        dots_indicator.setViewPager(viewPager)
        viewPager.setOffscreenPageLimit(0)
    }


}
