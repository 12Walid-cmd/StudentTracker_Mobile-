package ca.hccis.studenttracker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.hccis.studenttracker.entity.StudentTrackerTechnical

@Dao
interface StudentDao {

    @Query("SELECT * FROM students ORDER BY id ASC")
    suspend fun getAllStudents(): List<StudentTrackerTechnical>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentTrackerTechnical)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStudents(students: List<StudentTrackerTechnical>)

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()
}