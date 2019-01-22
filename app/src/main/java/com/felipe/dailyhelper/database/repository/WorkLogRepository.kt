package com.felipe.dailyhelper.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.felipe.dailyhelper.database.JDatabase
import com.felipe.dailyhelper.database.dao.WorkLogDao
import com.felipe.dailyhelper.database.entities.WorkLog

class WorkLogRepository(context: Context) {

    private val workLogDao: WorkLogDao

    init {
        val workLogDatabase = JDatabase.getInstance(context)
        workLogDao = workLogDatabase.getWorkLogDao()
    }

    companion object {

        private var sInstance: WorkLogRepository? = null

        fun getInstance(context: Context): WorkLogRepository {
            if (sInstance == null) {
                sInstance = WorkLogRepository(context)
            }
            return sInstance!!
        }

    }

    fun logFirstIn(workLog: WorkLog) {
        workLogDao.logFirstIn(workLog)
    }

    fun logFirstOut(firstIn: Long, firstOut: Long, id: Int) {
        workLogDao.logFirstOut(firstOut, firstOut - firstIn, id)
    }

    fun logSecondIn(firstOut: Long, secondIn: Long, id: Int) {
        workLogDao.logSecondIn(secondIn, secondIn - firstOut, id)
    }

    fun logSecondOut(secondIn: Long, secondOut: Long, id: Int) {
        workLogDao.logSecondIn(secondIn, secondOut - secondIn, id)
    }

    fun find(id: Int): WorkLog? {
        return workLogDao.find(id)
    }

    fun findAll(): LiveData<List<WorkLog>> {
        return workLogDao.findAll()
    }

    fun update(workLog: WorkLog) {
        return workLogDao.update(workLog)
    }

    fun delete(id: Int) {
        return workLogDao.delete(id)
    }

}
