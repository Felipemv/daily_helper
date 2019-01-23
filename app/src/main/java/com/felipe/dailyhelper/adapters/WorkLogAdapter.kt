package com.felipe.dailyhelper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felipe.dailyhelper.R
import com.felipe.dailyhelper.adapters.holders.WorkLogHolder
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.fragments.WorkFragment
import com.felipe.dailyhelper.listeners.OnItemClicked
import com.felipe.dailyhelper.util.DateUtil

class WorkLogAdapter(workLogs: List<WorkLog>, workLogClickListener: OnItemClicked.OnWorkLogItemClicked) :
    RecyclerView.Adapter<WorkLogHolder>() {

    private var workLogList: List<WorkLog> = workLogs
    private var listener: OnItemClicked.OnWorkLogItemClicked = workLogClickListener

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

        holder.layoutEdit.setOnClickListener {
            listener.onWorkLogClick(WorkFragment.BUTTON_EDIT, workLogList[position])
        }

        holder.layoutFinish.setOnClickListener {
            listener.onWorkLogClick(WorkFragment.BUTTON_FINISH, workLogList[position])
        }

        holder.layoutLogWork.setOnClickListener{
            listener.onWorkLogClick(WorkFragment.LAYOUT, workLogList[position])
        }
    }

    fun updateView(workLogList: List<WorkLog>) {
        this.workLogList = workLogList
        notifyDataSetChanged()
    }
}