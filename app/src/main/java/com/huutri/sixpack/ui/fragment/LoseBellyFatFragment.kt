package com.huutri.sixpack.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.huutri.sixpack.R
import com.huutri.sixpack.common.adapter.DayAdapter
import com.huutri.sixpack.common.base.BaseFragment
import com.huutri.sixpack.common.data.DatabaseAccess
import com.huutri.sixpack.common.model.DayDB
import com.huutri.sixpack.ui.activity.DetailActivity
import kotlinx.android.synthetic.main.fragment_lose_belly_fat.*

class LoseBellyFatFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lose_belly_fat, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    @SuppressLint("WrongConstant")
    private fun initView() {
        var arr = DatabaseAccess.getInstance(context!!).getALLDayLose
        rcv_Main_LoseBellyFat.layoutManager =
            LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rcv_Main_LoseBellyFat.adapter = DayAdapter(arr, { dayDB: DayDB -> partItemClicked(dayDB) })
        rcv_Main_LoseBellyFat.setHasFixedSize(true)


    }

    private fun partItemClicked(partItem: DayDB) {
        OneDayFragment.oneDayToGetData = partItem.ID_DAY!!
        OneDayFragment.totalexercise = partItem.SUM_NUMBER_EX_ON_DAY!!
        OneDayFragment.nameDay = partItem.NAME_DAY!!
        OneDayFragment.statusDay = partItem.STATUS_DAY!!

        startActivity(Intent(context, DetailActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        if (rcv_Main_LoseBellyFat != null && userVisibleHint) {
            var arr = DatabaseAccess.getInstance(context!!).getALLDayLose
            rcv_Main_LoseBellyFat.adapter =
                DayAdapter(arr, { dayDB: DayDB -> partItemClicked(dayDB) })
        }
    }


}