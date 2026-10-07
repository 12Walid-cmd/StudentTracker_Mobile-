package ca.hccis.studenttracker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class StudyTimerService : Service() {

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Study timer service started", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startTime = System.currentTimeMillis()
        getSharedPreferences("student_tracker_prefs", MODE_PRIVATE)
            .edit()
            .putLong("study_start_time", startTime)
            .apply()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        val prefs = getSharedPreferences("student_tracker_prefs", MODE_PRIVATE)
        val startTime = prefs.getLong("study_start_time", 0L)

        if (startTime > 0L) {
            val endTime = System.currentTimeMillis()
            val durationMillis = endTime - startTime
            val durationMinutes = durationMillis / 1000 / 60

            Toast.makeText(
                this,
                "Study session ended. Total time studied: $durationMinutes minute(s).",
                Toast.LENGTH_LONG
            ).show()

            prefs.edit().remove("study_start_time").apply()
        } else {
            Toast.makeText(this, "Study timer service stopped", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}