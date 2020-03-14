package com.huutri.sixpack.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huutri.sixpack.R
import com.huutri.sixpack.common.base.BaseActivity
import com.huutri.sixpack.common.data.DatabaseAccess
import com.huutri.sixpack.common.model.DayDB
import com.huutri.sixpack.ui.fragment.OneDayFragment
import kotlinx.android.synthetic.main.layout_day_item.view.*

class DayAdapter (val arrActionEdit: ArrayList<DayDB>,val clickListener: (DayDB) -> Unit ) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    companion object {
        var daygetCountExerciseFinish: Int = 0
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_day_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrActionEdit[position],clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return arrActionEdit.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(item: DayDB, clickListener: (DayDB) -> Unit) {
            itemView.tvDay.text = item.NAME_DAY
            itemView.setOnClickListener {
                clickListener(item)

            }
            when(item.STATUS_DAY){
                0->         {  //nothing
                    itemView.tvCompleteDay.text=itemView.context.getString(R.string.number_exrcises,item.SUM_NUMBER_EX_ON_DAY)
                    itemView.ivCompleteDay.visibility=View.GONE
                }
                1->{//rest
                    itemView.tvCompleteDay.text=itemView.context.getString(R.string.rest)
                    itemView.ivCompleteDay.visibility=View.VISIBLE
                    Glide.with(itemView).load(R.drawable.ic_rest).into( itemView.ivCompleteDay)
                }
                2->{ //finish
                    itemView.tvCompleteDay.text=itemView.context.getString(R.string.finish)
                    itemView.ivCompleteDay.visibility=View.VISIBLE
                    Glide.with(itemView).load(R.drawable.ic_check).into( itemView.ivCompleteDay)
                }
                3->{ //continue
                    daygetCountExerciseFinish=item.ID_DAY!!
                     var countExerciseFinish=   DatabaseAccess.getInstance(itemView.context!!).countExerciseFinish  //get total exercise finish
                    var     percentComplete=((countExerciseFinish.toDouble() / item.SUM_NUMBER_EX_ON_DAY!!)*100).toInt()
                    itemView.tvCompleteDay.text=percentComplete.toString()+"% "+itemView.context.getString(R.string.completed)
                    itemView.ivCompleteDay.visibility=View.GONE
                    itemView.rltItem.setBackgroundResource(R.drawable.bg_item_rcv_selector_continue)
                    itemView.btnStart.visibility=View.VISIBLE
                    itemView.tvDay.setTextColor(itemView.context.resources.getColor(R.color.white))
                    itemView.tvCompleteDay.setTextColor(itemView.context.resources.getColor(R.color.white))
                }
            }
        }
    }

}