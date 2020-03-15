package com.huutri.sixpack.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
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
    private val TAG_FRAGMENT_ONE = "HomeFragment"
    private val TAG_FRAGMENT_TWO = "ReportFragment"
    private val TAG_FRAGMENT_THREE = "MeFragment"

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_training -> {


                        var isCreate = false
                        var fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE)
                        if (fragment == null) {
                            fragment = HomeFragment()
                            isCreate = true

                        }
                        replaceFragmentwithTag(fragment, isCreate, TAG_FRAGMENT_ONE)
                        return true
                    }
//                    R.id.navigation_report -> {
//
//
//                        var isCreate = false
//                        var fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO)
//                        if (fragment == null) {
//                            fragment = ReportFragment()
//                            isCreate = true
//
//                        }
//                        replaceFragmentwithTag(fragment, isCreate, TAG_FRAGMENT_TWO)
//
//                        return true
//                    }
                    R.id.navigation_me -> {
                        var isCreate = false

                        var fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE)
                        if (fragment == null) {
                            fragment = MeFragment()
                            isCreate = true

                        }
                        replaceFragmentwithTag(fragment, isCreate, TAG_FRAGMENT_THREE)

                        return true
                    }
                }
                return false
            }
        }
    /**
     * add fragment has tag
     */
    private fun replaceFragmentwithTag(fragment: Fragment, isCreate: Boolean, tag: String) {
        if (fragment != currentFragment) {
            if (isCreate) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment, tag)
                    .commit()

            } else {
                supportFragmentManager
                    .beginTransaction().show(fragment).commitNow()
            }
            when (tag) {
                TAG_FRAGMENT_ONE -> {
//                    hideFragmentwithTag(TAG_FRAGMENT_TWO)
                    hideFragmentwithTag(TAG_FRAGMENT_THREE)
                }
//                TAG_FRAGMENT_TWO -> {
//                    hideFragmentwithTag(TAG_FRAGMENT_ONE)
//                    hideFragmentwithTag(TAG_FRAGMENT_THREE)
//                }
                TAG_FRAGMENT_THREE -> {
                   // hideFragmentwithTag(TAG_FRAGMENT_TWO)
                    hideFragmentwithTag(TAG_FRAGMENT_ONE)
                }

            }

            currentFragment = fragment
        }
    }

    /**
     * hide fragment with tag
     */
    private fun hideFragmentwithTag(tag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction().hide(fragment).commitNow()
        }
    }
}
