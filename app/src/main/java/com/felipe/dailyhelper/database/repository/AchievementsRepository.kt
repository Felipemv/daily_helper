package com.felipe.dailyhelper.database.repository

import android.content.Context
import com.felipe.dailyhelper.database.JDatabase
import com.felipe.dailyhelper.database.dao.AchievementsDao
import com.felipe.dailyhelper.database.entities.Achievements

class AchievementsRepository(context: Context) {

    private val achievementsDao: AchievementsDao

    init {
        val achievementsDatabase = JDatabase.getInstance(context)
        achievementsDao = achievementsDatabase.getAchievementsDao()
    }

    companion object {
        private var sInstance: AchievementsRepository? = null

        fun getInstance(context: Context): AchievementsRepository {
            if (sInstance == null) {
                sInstance = AchievementsRepository(context)
            }
            return sInstance!!
        }
    }

    fun insert(achievement: Achievements) {
        achievementsDao.insert(achievement)
    }

    fun findAll(): List<Achievements> {
        return achievementsDao.findAll()
    }

    fun findAllBySalaryId(salaryId: Int): List<Achievements> {
        return achievementsDao.findAllBySalaryId(salaryId)
    }

    fun update(achievement: Achievements) {
        achievementsDao.update(achievement)
    }

    fun delete(id: Int) {
        achievementsDao.delete(id)
    }
}