package com.felipe.dailyhelper.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "salary")
data class Salary(
    var hour: Float = 0f,

    @ColumnInfo(name = "week_load")
    var weekLoad: Int = 44,

    @ColumnInfo(name = "payday_description")
    var paydayDescription: String = "",

    @ColumnInfo(name = "has_advance")
    var hasAdvance: Boolean = false,

    @ColumnInfo(name = "advance_description")
    var advanceDescription: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "date_start")
    var dateStart: Long = 0L
}