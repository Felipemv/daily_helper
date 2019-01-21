package com.felipe.dailyhelper.database.repository

import android.content.Context
import com.felipe.dailyhelper.database.JDatabase
import com.felipe.dailyhelper.database.dao.UserDao
import com.felipe.dailyhelper.database.entities.User

class UserRepository(context: Context) {

    private val userDao: UserDao

    init {
        val userDatabase = JDatabase.getInstance(context)
        userDao = userDatabase.getUserDao()
    }

    companion object {

        private var sInstance: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (sInstance == null) {
                sInstance = UserRepository(context)
            }
            return sInstance!!
        }

    }

    fun insert(user: User) {
        userDao.insert(user)
    }

    fun exists(username: String): Boolean {
        return (userDao.exists(username) != null)
    }

    fun find(id: Int): User? {
        return userDao.find(id)
    }

    fun update(user: User) {
        userDao.update(user)
    }

    fun delete(id: Int) {
        return userDao.delete(id)
    }

    fun login(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    fun logout(id: Int): Boolean {
        return userDao.find(id) != null
    }

}