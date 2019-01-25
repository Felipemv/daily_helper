package com.felipe.dailyhelper.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.felipe.dailyhelper.database.entities.Salary

@Dao
interface SalaryDao {

    @Insert
    fun insert(salary: Salary)

    @Query("SELECT * FROM salary")
    fun findAll(): List<Salary>

    @Query("SELECT * FROM salary WHERE date_start IN (SELECT MAX(date_start) FROM salary)")
    fun findLastSalary(): LiveData<Salary>
}