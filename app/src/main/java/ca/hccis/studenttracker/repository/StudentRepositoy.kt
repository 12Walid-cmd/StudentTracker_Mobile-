package ca.hccis.studenttracker.repository

import ca.hccis.studenttracker.dao.StudentDao
import ca.hccis.studenttracker.entity.StudentTrackerTechnical
import ca.hccis.studenttracker.network.ApiClient

class StudentRepository(
    private val studentDao: StudentDao
) {

    suspend fun getStudentsFromApi(): List<StudentTrackerTechnical> {
        return ApiClient.studentApi.getStudents()
    }

    suspend fun getStudentsFromRoom(): List<StudentTrackerTechnical> {
        return studentDao.getAllStudents()
    }

    suspend fun insertStudentIntoRoom(student: StudentTrackerTechnical) {
        studentDao.insertStudent(student)
    }

    suspend fun insertAllStudentsIntoRoom(students: List<StudentTrackerTechnical>) {
        studentDao.insertAllStudents(students)
    }

    suspend fun clearRoomStudents() {
        studentDao.deleteAllStudents()
    }

    suspend fun addStudentToApi(student: StudentTrackerTechnical): StudentTrackerTechnical {
        return ApiClient.studentApi.addStudent(student)
    }
}