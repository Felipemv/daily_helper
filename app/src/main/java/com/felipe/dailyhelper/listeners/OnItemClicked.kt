package com.felipe.dailyhelper.listeners

import com.felipe.dailyhelper.database.entities.WorkLog

interface OnItemClicked {

    interface OnWorkLogItemClicked {
        fun onWorkLogClick(id: Int, workLog: WorkLog)
    }
}