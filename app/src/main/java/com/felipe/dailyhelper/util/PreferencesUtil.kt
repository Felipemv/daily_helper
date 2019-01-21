package com.felipe.dailyhelper.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil(context: Context) {

    private val FILE_NAME = "daily_helper.prefs"
    private val USER_ID = "user_id"
    private val USERNAME = "username"
    private val PASSWORD = "password"

    private val preferences: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    var userId: Int
        get() = preferences.getInt(USER_ID, -1)
        set(value) = preferences.edit().putInt(USER_ID, value).apply()

    var username: String?
        get() = preferences.getString(USERNAME, "")
        set(value) = preferences.edit().putString(USERNAME, value).apply()

    var password: String?
        get() = preferences.getString(PASSWORD, "")
        set(value) = preferences.edit().putString(PASSWORD, value).apply()
}