package com.felipe.dailyhelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.database.repository.WorkLogRepository

class WorkLogListViewModel(application: Application) : AndroidViewModel(application) {

    private val workLogRepository = WorkLogRepository.getInstance(application)
    private val workLogList = MediatorLiveData<List<WorkLog>>()

    init {
        getAllWorkLog()
    }

    fun getWorkLogList(): LiveData<List<WorkLog>> {
        return workLogList
    }

    fun getAllWorkLog() {
        workLogList.addSource(workLogRepository.findAll()) { workLogs ->
            workLogList.postValue(workLogs)
        }
    }
}