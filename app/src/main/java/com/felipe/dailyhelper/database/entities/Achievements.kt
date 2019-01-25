package com.felipe.dailyhelper.database.entities

import androidx.room.*
import com.felipe.dailyhelper.util.DateUtil

@Entity(tableName = "achievements")
data class Achievements(
    @ColumnInfo(name = "initial_date")
    var initialDate: Long = 0L,

    @ColumnInfo(name = "final_date")
    var finalDate: Long = 0L,

    var total: Long = 0L,

    var money: Float = 0F,

    var start: Long = DateUtil.getCurrentTimeInMillis()

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Embedded(prefix = "salary_")
    var salary: Salary? = null
}