package com.felipe.dailyhelper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.adapters.holders.HistoryHolder
import com.felipe.dailyhelper.adapters.holders.WorkLogHolder
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.listeners.OnItemClicked
import com.felipe.dailyhelper.util.DateUtil

class HistoryAdapter(history: List<WorkLog>, historyCLickListener: OnItemClicked.OnHistoryItemClicked) :
    RecyclerView.Adapter<HistoryHolder>() {

    private var history: List<WorkLog> = history
    private var listener: OnItemClicked.OnHistoryItemClicked = historyCLickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_history_work_log,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val date = DateUtil.dateLongToString(history[position].date)
        val morning = DateUtil.timeLongToString(history[position].firstIn) + " - " +
                DateUtil.timeLongToString(history[position].firstOut)

        val afternoon = DateUtil.timeLongToString(history[position].secondIn) + " - " +
                DateUtil.timeLongToString(history[position].secondOut)

        holder.tvDate.text = date
        holder.tvMorning.text = morning
        holder.tvAfternoon.text = afternoon

    }

    fun updateView(history: List<WorkLog>) {
        this.history = history
        notifyDataSetChanged()
    }
}