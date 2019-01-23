package com.felipe.dailyhelper.adapters.holders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R

class WorkLogHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvDate: TextView
    var tvFirstIn: TextView
    var tvFirstOut: TextView
    var tvSecondIn: TextView
    var tvSecondOut: TextView
    var tvTotal: TextView
    var tvLunchTime: TextView
    var layoutEdit: LinearLayout
    var layoutFinish: LinearLayout

    init {
        tvDate = itemView.findViewById(R.id.tv_wl_date)
        tvFirstIn = itemView.findViewById(R.id.tv_wl_first_in)
        tvFirstOut = itemView.findViewById(R.id.tv_wl_first_out)
        tvSecondIn = itemView.findViewById(R.id.tv_wl_second_in)
        tvSecondOut = itemView.findViewById(R.id.tv_wl_second_out)
        tvTotal = itemView.findViewById(R.id.tv_wl_total)
        tvLunchTime = itemView.findViewById(R.id.tv_wl_lunch)
        layoutEdit = itemView.findViewById(R.id.ll_wl_edit)
        layoutFinish = itemView.findViewById(R.id.ll_wl_finish)
    }
}