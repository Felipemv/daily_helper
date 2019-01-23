package com.felipe.dailyhelper.adapters.holders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R

class WorkLogHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvDate: TextView = itemView.findViewById(R.id.tv_wl_date)
    var tvFirstIn: TextView = itemView.findViewById(R.id.tv_wl_first_in)
    var tvFirstOut: TextView = itemView.findViewById(R.id.tv_wl_first_out)
    var tvSecondIn: TextView = itemView.findViewById(R.id.tv_wl_second_in)
    var tvSecondOut: TextView = itemView.findViewById(R.id.tv_wl_second_out)
    var tvTotal: TextView = itemView.findViewById(R.id.tv_wl_total)
    var tvLunchTime: TextView = itemView.findViewById(R.id.tv_wl_lunch)
    var layoutEdit: LinearLayout = itemView.findViewById(R.id.ll_wl_edit)
    var layoutFinish: LinearLayout = itemView.findViewById(R.id.ll_wl_finish)
    var layoutLogWork: LinearLayout = itemView.findViewById(R.id.ll_log_work)

}