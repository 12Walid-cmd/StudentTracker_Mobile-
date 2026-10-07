package ca.hccis.studenttracker.bo

import ca.hccis.studenttracker.entity.StudentTrackerTechnical


object StudentTrackerBo {
    const val POINTS_PER_MINUTE = 1
    const val POINTS_DAILY_GOAL = 50
    const val POINTS_WEEKLY_GOAL = 100
    const val POINTS_NOTES = 10
    const val POINTS_ACTIVE_METHOD = 20

    /**
     * Calculate the study score based on study attributes
     *
     * @return the study score
     * @author YourName
     * @since 20260116
     */
    fun calculateStudyScore(student: StudentTrackerTechnical): Int {
        var score = 0

        // Minutes studied
        score += (student.studyDurationMinutes ?: 0) * POINTS_PER_MINUTE

        // Daily goal achieved
        if ((student.studyDurationMinutes ?: 0) >= (student.dailyStudyGoal ?: Int.MAX_VALUE)) {
            score += POINTS_DAILY_GOAL
        }

        // Weekly goal achieved
        if ((student.weeklyStudyGoal ?: 0) > 0) {
            score += POINTS_WEEKLY_GOAL
        }

        // Notes bonus
        if (!student.notes.isNullOrBlank()) {
            score += POINTS_NOTES
        }

        // Study method bonus
        if (!student.studyMethod.isNullOrBlank()) {
            score += POINTS_ACTIVE_METHOD
        }

        student.studyScore = score
        return score
    }
}