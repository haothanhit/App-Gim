package com.huutri.photoeditor.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.huutri.sixpack.ui.fragment.*

class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter (fm) {
 
    private val COUNT = 3
 
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = LoseBellyFatFragment()
            1 -> fragment = RockHardFragment()
            2 -> fragment = SixPackFragment()
        }
 
        return fragment!!
    }
 
    override fun getCount(): Int {
        return COUNT
    }
 
    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }

}
