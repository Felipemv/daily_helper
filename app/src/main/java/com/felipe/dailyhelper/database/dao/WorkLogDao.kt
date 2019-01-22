package com.felipe.dailyhelper.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.felipe.dailyhelper.database.entities.WorkLog

@Dao
interface WorkLogDao {

    @Insert
    fun logFirstIn(workLog: WorkLog)

    @Query("UPDATE work_log SET first_out = :firstOut AND total = :total WHERE id = :id")
    fun logFirstOut(firstOut: Long, total: Long, id: Int)

    @Query("UPDATE work_log SET first_out = :secondIn AND lunch_time = :lunchTime WHERE id = :id")
    fun logSecondIn(secondIn: Long, lunchTime: Long, id: Int)

    @Query("UPDATE work_log SET first_out = :secondOut AND total = :total WHERE id = :id")
    fun logSecondOut(secondOut: Long, total: Long, id: Int)

    @Query("SELECT * FROM work_log WHERE id = :id")
    fun find(id: Int): WorkLog?

    @Query("SELECT * FROM work_log")
    fun findAll(): LiveData<List<WorkLog>>

    @Update
    fun update(workLog: WorkLog)

    @Query("DELETE FROM work_log WHERE id = :id")
    fun delete(id: Int)
}