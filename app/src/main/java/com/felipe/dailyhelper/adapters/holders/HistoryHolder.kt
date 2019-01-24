package com.felipe.dailyhelper.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R

class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvDate: TextView = itemView.findViewById(R.id.tv_date_history)
    var tvMorning: TextView = itemView.findViewById(R.id.tv_morning_history)
    var tvAfternoon: TextView = itemView.findViewById(R.id.tv_afternoon_history)
}