package com.felipe.dailyhelper.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.felipe.dailyhelper.database.entities.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun find(id: Int): User?

    @Query("SELECT * FROM user WHERE username = :username")
    fun exists(username: String): User?

    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id: Int)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User?
}