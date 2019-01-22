package com.felipe.dailyhelper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.adapters.holders.WorkLogHolder
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.util.DateUtil

class WorkLogAdapter(workLogs: List<WorkLog>) : RecyclerView.Adapter<WorkLogHolder>() {

    private var workLogList: List<WorkLog> = workLogs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkLogHolder {
        return WorkLogHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_work_log, parent, false))
    }

    override fun getItemCount(): Int {
        return workLogList.size
    }

    override fun onBindViewHolder(holder: WorkLogHolder, position: Int) {
        holder.tvDate.text = DateUtil.dateLongToString(workLogList[position].date)
        holder.tvFirstIn.text = DateUtil.timeLongToString(workLogList[position].firstIn)
        holder.tvFirstOut.text = DateUtil.timeLongToString(workLogList[position].firstOut)
        holder.tvSecondIn.text = DateUtil.timeLongToString(workLogList[position].secondIn)
        holder.tvSecondOut.text = DateUtil.timeLongToString(workLogList[position].secondOut)
        holder.tvTotal.text = DateUtil.timeLongToString(workLogList[position].total)
        holder.tvLunchTime.text = DateUtil.timeLongToString(workLogList[position].lunchTime)
    }
}