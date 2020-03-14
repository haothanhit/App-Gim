package com.huutri.sixpack.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huutri.sixpack.R
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.ui.fragment.HomeFragment
import com.huutri.sixpack.ui.fragment.MeFragment
import com.huutri.sixpack.ui.fragment.ReportFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(R.id.container, HomeFragment(), false)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_training -> {
                        replaceFragment(R.id.container, HomeFragment(), false)
                        return true
                    }
                    R.id.navigation_report -> {
                        replaceFragment(R.id.container, ReportFragment(), false)

                        return true
                    }
                    R.id.navigation_me -> {
                        replaceFragment(R.id.container, MeFragment(), false)

                        return true
                    }
                }
                return false
            }
        }

}
