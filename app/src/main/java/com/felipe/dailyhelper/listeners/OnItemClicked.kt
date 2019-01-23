package com.felipe.dailyhelper.listeners

interface OnItemClicked {

    interface OnWorkLogItemClicked {
        fun onWorkLogClick(component: Int, id: Int)
    }
}