package com.huutri.sixpack.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.huutri.sixpack.R
import com.huutri.sixpack.common.model.DayDB
import com.huutri.sixpack.common.model.Exercise_Move
import com.huutri.sixpack.ui.fragment.OneDayFragment
import kotlinx.android.synthetic.main.layout_day_item.view.*
import kotlinx.android.synthetic.main.layout_one_day_item.view.*

class OneDayAdapter(
    val clickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<OneDayAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_one_day_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(OneDayFragment.arrExercise_Move!!, position, clickListener)
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return OneDayFragment.arrExercise_Move!!.listExercise!!.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(item: Exercise_Move, pos: Int, clickListener: (Int) -> Unit) {
            itemView.tvTime.text = item.listExercise!!.get(pos).TIME_EX
            itemView.tvTitle.text = item.listMove!!.get(pos).NAME_MOVE
            try {
                Glide.with(itemView).load(
                    itemView.resources.getIdentifier(
                        item.listMove?.get(pos)?.LINK_IMAGE_MOVE,
                        "drawable",
                        "com.huutri.sixpack"
                    )
                ).into(itemView.ivOneDay)

            } catch (ex: Exception) {
            }
            itemView.setOnClickListener {
                clickListener( pos)
            }

            when(item.listExercise!!.get(pos).STATUS_EX){
                0->         {  //nothing
                   // itemView.tvCompleteDay.text=itemView.context.getString(R.string.number_exrcises,item.SUM_NUMBER_EX_ON_DAY)
                    itemView.ivDone.visibility=View.INVISIBLE
                }
                1->{

                }
                2->{ //finish
                    itemView.ivDone.visibility=View.VISIBLE
                }

            }
        }
    }

}