package com.felipe.dailyhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.felipe.dailyhelper.database.dao.UserDao
import com.felipe.dailyhelper.database.dao.WorkLogDao
import com.felipe.dailyhelper.database.entities.User
import com.felipe.dailyhelper.database.entities.WorkLog

@Database(entities = [User::class, WorkLog::class], version = 1)
abstract class JDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getWorkLogDao(): WorkLogDao

    companion object {
        private val lock = Any()
        private const val DATABASE = "dailyhelper.db"
        private var sInstance: JDatabase? = null

        fun getInstance(context: Context): JDatabase {
            synchronized(lock) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, JDatabase::class.java, DATABASE)
                        .allowMainThreadQueries()
                        .build()
                }
                return sInstance!!
            }
        }
    }
}