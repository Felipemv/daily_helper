package com.felipe.dailyhelper.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var phone: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo
    var online: Boolean = false
}