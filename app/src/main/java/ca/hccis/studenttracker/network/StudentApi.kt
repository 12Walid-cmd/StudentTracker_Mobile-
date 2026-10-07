package ca.hccis.studenttracker.network

import ca.hccis.studenttracker.entity.StudentTrackerTechnical
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface StudentApi {

    @GET("students")
    suspend fun getStudents(): List<StudentTrackerTechnical>

    @POST("students")
    suspend fun addStudent(@Body student: StudentTrackerTechnical): StudentTrackerTechnical
}