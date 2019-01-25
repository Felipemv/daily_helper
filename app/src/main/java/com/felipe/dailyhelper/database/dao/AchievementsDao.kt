package com.felipe.dailyhelper.database.dao

import androidx.room.*
import com.felipe.dailyhelper.database.entities.Achievements

@Dao
interface AchievementsDao {

    @Insert
    fun insert(achievement: Achievements)

    @Query("SELECT * FROM achievements")
    fun findAll(): List<Achievements>

    @Query("SELECT * FROM achievements WHERE salary_id = :salaryId")
    fun findAllBySalaryId(salaryId: Int): List<Achievements>

    @Update
    fun update(achievement: Achievements)

    @Query("DELETE FROM achievements WHERE id = :id")
    fun delete(id: Int)
}