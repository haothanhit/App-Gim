package com.huutri.sixpack.ui.fragment

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.huutri.sixpack.R
import com.huutri.sixpack.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : BaseFragment() {
    var dialog: Dialog? = null  //show dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        //banner ads
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        //native ads
        var adLoader = AdLoader.Builder(context!!, getString(R.string.ad_native_id))
            .forUnifiedNativeAd(object : UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
                override fun onUnifiedNativeAdLoaded(unifiedNativeAd: UnifiedNativeAd) {
                    try {
                        val styles = NativeTemplateStyle.Builder().withMainBackgroundColor(
                            context!!.getDrawable(R.color.white) as ColorDrawable?
                        ).build()
                        my_template.setStyles(styles)
                        my_template.setNativeAd(unifiedNativeAd)
                    } catch (ex: Exception) {
                    }
                }
            }).build()
        adLoader.loadAd(AdRequest.Builder().build())
        tvEditBMI.setOnClickListener {
            showDialogWeightHeight()
        }
        tvEditHeight.setOnClickListener {
            showDialogWeightHeight()
        }
    }

    var isKG: Boolean = false
    var isCM: Boolean = false
    private fun showDialogWeightHeight() {
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
        else {
            dialog = Dialog(context!!)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setContentView(R.layout.dialog_weight_height)
            dialog!!.getWindow()!!.setBackgroundDrawableResource(R.color.white)
            val btnKG = dialog!!.findViewById(R.id.btnKG) as Button
            val btnLB = dialog!!.findViewById(R.id.btnLB) as Button
            val btnCM = dialog!!.findViewById(R.id.btnCM) as Button
            val btnIN = dialog!!.findViewById(R.id.btnIN) as Button
            val btnSave = dialog!!.findViewById(R.id.btnSave) as TextView
            val btnCanel = dialog!!.findViewById(R.id.btnCanel) as TextView
            var edtWeight = dialog!!.findViewById(R.id.edtWeight) as EditText
            val edtHeight1 = dialog!!.findViewById(R.id.edtHeight1) as EditText
            val edtHeight2 = dialog!!.findViewById(R.id.edtHeight2) as EditText
            btnKG.setOnClickListener {
                if (!isKG) {
                    isKG = true
                    setTextWeight(edtWeight)
                }
            }
            btnLB.setOnClickListener {
                if (isKG) {
                    isKG = false
                    setTextWeight(edtWeight)

                }
            }
            btnCanel.setOnClickListener {
                dialog!!.dismiss()
            }
            edtWeight.setOnFocusChangeListener { view, focus ->
                if (!focus) {//not has focus
                    setTextWeight(edtWeight)
                }

            }
            edtWeight.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    var index = edtWeight.text.trim().indexOf(' ')
                    var text: String
                    if (index > 0) {
                        text = edtWeight.text.trim().substring(0, index)

                    } else {
                        text = edtWeight.text.trim().toString()

                    }
                    edtWeight.setText(text)

                    return false
                }
            })
            dialog!!.show()
        }
    }
     fun convertKGtoLB(){

     }
    fun setTextWeight(edtWeight: EditText) {
        var index = edtWeight.text.trim().indexOf(' ')
        var text = ""
        if (index > 0) {
            text = edtWeight.text.trim().substring(0, index)
        } else {
            text = edtWeight.text.trim().toString()
        }
        if (text.equals("") || text.isEmpty()) text = "0"
        if (isKG)   //choose KG
        {
            text += " KG"
        } else {     //choose LB
            text += " LB"

        }
        edtWeight.setText(text)

    }
}
