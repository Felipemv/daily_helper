package com.felipe.dailyhelper.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {

        private val TIME_ZONE = Calendar.getInstance().timeZone.rawOffset

        private const val DATE = "dd/mm/yyyy"
        private const val TIME = "HH:mm:ss"
        private const val MINUTE_IN_MILLIS = 60 * 1000
        private const val HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS
        private const val SUMMER_TIME = 1 * HOUR_IN_MILLIS
        private const val LANGUAGE_TAG = "pt-br"

        private val dateFormat = SimpleDateFormat(DATE, Locale.forLanguageTag(LANGUAGE_TAG))
        private val timeFormat = SimpleDateFormat(TIME, Locale.forLanguageTag(LANGUAGE_TAG))

        fun dateStringToLong(date: String): Long {
            return dateFormat.parse(date).time - MINUTE_IN_MILLIS
        }

        fun dateLongToString(date: Long): String {
            if (date > 0) {
                val d = dateFormat.format(Date(date + SUMMER_TIME))
                val split = d.split("/")

                return split[0] + "/" + split[1].toInt() + 1 + "/" + split[2]
            }
            return ""
        }

        fun timeStringToLong(time: String, date: String): Long {
            return timeFormat.parse("$time:00").time + dateStringToLong(date) + TIME_ZONE
        }

        fun timeLongToString(time: Long): String {
            return if (time > 0) timeFormat.format(Date(time)) else "00:00:00"
        }

        fun getCurrentTimeInMillis(): Long {
            return Calendar.getInstance().timeInMillis
        }

        fun getCurrentStringDate(): String {
            val calendar = Calendar.getInstance()
            val current = calendar.timeInMillis + MINUTE_IN_MILLIS
            calendar.time = Date(current)
            return (
                    "" + calendar.get(Calendar.DAY_OF_MONTH)
                            + "/" + calendar.get(Calendar.MONTH) + 1
                            + "/" + calendar.get(Calendar.YEAR)
                    )


//            return dateLongToString(DateUtil.getCurrentTimeInMillis())
        }

        fun getCurrentStringTime(): String {
            val time = timeLongToString(DateUtil.getCurrentTimeInMillis() - MINUTE_IN_MILLIS)
            val t = time.split(":")
            return t[0] + ":" + t[1]
        }

        fun calculateTime(time: Long): String {
            val hours = (time / HOUR_IN_MILLIS).toInt()
            val minutes = ((time - hours * HOUR_IN_MILLIS) / MINUTE_IN_MILLIS).toInt()

            val h = if (hours < 10) "0$hours" else hours.toString()
            val m = if (minutes < 10) "0$minutes" else minutes.toString()

            return "$h:$m:00"
        }
    }
}