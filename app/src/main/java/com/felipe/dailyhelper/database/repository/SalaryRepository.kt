package com.felipe.dailyhelper.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.felipe.dailyhelper.database.JDatabase
import com.felipe.dailyhelper.database.dao.SalaryDao
import com.felipe.dailyhelper.database.entities.Salary

class SalaryRepository(context: Context) {

    private val salaryDao: SalaryDao

    init {
        val salaryDatabase = JDatabase.getInstance(context)
        salaryDao = salaryDatabase.getSalaryDao()
    }

    companion object {
        private var sInstance: SalaryRepository? = null

        fun getInstance(context: Context): SalaryRepository {
            if (sInstance == null) {
                sInstance = SalaryRepository(context)
            }
            return sInstance!!
        }
    }

    fun insert(salary: Salary) {
        salaryDao.insert(salary)
    }

    fun findAll(): List<Salary> {
        return salaryDao.findAll()
    }

    fun findLastSalary(): LiveData<Salary> {
        return salaryDao.findLastSalary()
    }
}