package com.felipe.dailyhelper.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_log")
data class WorkLog(

    var date: Long,

    @ColumnInfo(name = "first_in")
    var firstIn: Long

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "first_out")
    var firstOut: Long = 0

    @ColumnInfo(name = "second_in")
    var secondIn: Long = 0

    @ColumnInfo(name = "second_out")
    var secondOut: Long = 0

    @ColumnInfo(name = "lunch_time")
    var lunchTime: Long = 0

    var total: Long = 0

}