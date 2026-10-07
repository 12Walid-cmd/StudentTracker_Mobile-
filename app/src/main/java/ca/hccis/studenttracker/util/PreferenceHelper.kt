package ca.hccis.studenttracker.util

import android.content.Context

class PreferenceHelper(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("student_tracker_prefs", Context.MODE_PRIVATE)

    fun saveLastStudentName(value: String) {
        sharedPreferences.edit().putString("last_student_name", value).apply()
    }

    fun getLastStudentName(): String {
        return sharedPreferences.getString("last_student_name", "") ?: ""
    }

    fun saveLastSubject(value: String) {
        sharedPreferences.edit().putString("last_subject", value).apply()
    }

    fun getLastSubject(): String {
        return sharedPreferences.getString("last_subject", "") ?: ""
    }

    fun saveDailyGoal(value: String) {
        sharedPreferences.edit().putString("daily_goal", value).apply()
    }

    fun getDailyGoal(): String {
        return sharedPreferences.getString("daily_goal", "") ?: ""
    }

    fun saveWeeklyGoal(value: String) {
        sharedPreferences.edit().putString("weekly_goal", value).apply()
    }

    fun getWeeklyGoal(): String {
        return sharedPreferences.getString("weekly_goal", "") ?: ""
    }
}