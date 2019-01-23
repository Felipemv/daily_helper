package com.felipe.dailyhelper.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {

        private val TIME_ZONE = Calendar.getInstance().timeZone.rawOffset

        private const val DATE = "dd/mm/yyyy"
        private const val TIME = "hh:mm:ss"
        private const val MINUTE_IN_MILLIS = 60 * 1000
        private const val HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS
        private const val SUMMER_TIME = 1 * HOUR_IN_MILLIS
        private const val LANGUAGE_TAG = "pt-br"

        private val dateFormat = SimpleDateFormat(DATE, Locale.forLanguageTag(LANGUAGE_TAG))
        private val timeFormat = SimpleDateFormat(TIME, Locale.forLanguageTag(LANGUAGE_TAG))

        fun dateStringToLong(date: String): Long {
            return dateFormat.parse(date).time + TIME_ZONE + SUMMER_TIME
        }

        fun dateLongToString(date: Long): String {
            return if (date > 0) dateFormat.format(Date(date + SUMMER_TIME)) else ""
        }

        fun timeStringToLong(time: String, date: String): Long {
            return timeFormat.parse("$time:00").time + dateStringToLong(date) - SUMMER_TIME
        }

        fun timeLongToString(time: Long): String {
            return if (time > 0) timeFormat.format(Date(time - MINUTE_IN_MILLIS)) else "00:00:00"
        }
    }
}