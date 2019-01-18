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

    fun find(): User? {
        return userDao.find()
    }

    fun update(user: User) {
        userDao.update(user)
    }

    fun delete(id: Int) {
        return userDao.delete(id)
    }

    fun login(username: String, password: String): Boolean {
        val user = userDao.login(username, password)
        if (user != null) {
            user.online = true
            userDao.update(user)
            return true
        }
        return false
    }

    fun logout(): Boolean {
        val user = userDao.find()
        if (user != null) {
            user.online = false
            userDao.update(user)
            return true
        }
        return false
    }

}