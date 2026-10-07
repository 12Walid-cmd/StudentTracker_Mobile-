package ca.hccis.studenttracker.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Objects

@Entity(tableName = "students")
class StudentTrackerTechnical {

    @PrimaryKey
    var id: Int? = 0

    var studyDurationMinutes: Int? = null
    var studentName: String? = null
    var subject: String? = null
    var studyDate: String? = null
    var studyMethod: String? = null
    var dailyStudyGoal: Int? = null
    var weeklyStudyGoal: Int? = null
    var notes: String? = null
    var studyScore: Int? = null

    constructor()

    constructor(
        studyDurationMinutes: Int?,
        id: Int?,
        studentName: String?,
        subject: String?,
        studyDate: String?,
        studyMethod: String?,
        dailyStudyGoal: Int?,
        weeklyStudyGoal: Int?,
        notes: String?,
        studyScore: Int? = null
    ) {
        this.studyDurationMinutes = studyDurationMinutes
        this.id = id
        this.studentName = studentName
        this.subject = subject
        this.studyDate = studyDate
        this.studyMethod = studyMethod
        this.dailyStudyGoal = dailyStudyGoal
        this.weeklyStudyGoal = weeklyStudyGoal
        this.notes = notes
        this.studyScore = studyScore
    }

    override fun toString(): String {
        return "StudentTrackerTechnical\n" +
                "    id                   = $id,\n" +
                "    studyDurationMinutes = $studyDurationMinutes,\n" +
                "    studentName          = '$studentName',\n" +
                "    subject              = '$subject',\n" +
                "    studyDate            = '$studyDate',\n" +
                "    studyMethod          = '$studyMethod',\n" +
                "    dailyStudyGoal       = $dailyStudyGoal,\n" +
                "    weeklyStudyGoal      = $weeklyStudyGoal,\n" +
                "    notes                = '$notes',\n" +
                "    studyScore           = $studyScore\n"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is StudentTrackerTechnical) return false
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(this.id)
    }
}