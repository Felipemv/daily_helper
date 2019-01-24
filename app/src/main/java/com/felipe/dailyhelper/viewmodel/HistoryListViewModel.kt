package com.felipe.dailyhelper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.felipe.dailyhelper.database.entities.WorkLog
import com.felipe.dailyhelper.database.repository.WorkLogRepository

class HistoryListViewModel(application: Application) : AndroidViewModel(application) {

    private val historyRepository = WorkLogRepository.getInstance(application)
    private val historyList = MediatorLiveData<List<WorkLog>>()

    init {
        getAllWorkLog()
    }

    fun getHistory(): LiveData<List<WorkLog>> {
        return historyList
    }

    private fun getAllWorkLog() {
        historyList.addSource(historyRepository.findAll()) { history ->
            historyList.postValue(history)
        }
    }
}