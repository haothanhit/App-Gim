package com.huutri.sixpack.ui.activity

import android.os.Bundle
import com.huutri.sixpack.R
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.ui.fragment.OneDayFragment
import com.huutri.sixpack.ui.fragment.RestFragment

class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if(OneDayFragment.totalexercise==0){
            replaceFragment(R.id.container, RestFragment(), false)

        }else
        replaceFragment(R.id.container, OneDayFragment(), false)

    }
}