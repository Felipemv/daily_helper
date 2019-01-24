package com.felipe.dailyhelper.listeners

import com.felipe.dailyhelper.database.entities.WorkLog

interface OnItemClicked {

    interface OnWorkLogItemClicked {
        fun onWorkLogClick(id: Int, history: WorkLog)
    }

    interface OnHistoryItemClicked {
        fun onHistoryClick(id: Int, history: WorkLog)
    }
}