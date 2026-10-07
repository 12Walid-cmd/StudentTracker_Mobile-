package ca.hccis.studenttracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ca.hccis.studenttracker.db.StudentDatabase
import ca.hccis.studenttracker.entity.StudentTrackerTechnical
import ca.hccis.studenttracker.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val studentDao = StudentDatabase.getDatabase(application).studentDao()
    private val repository = StudentRepository(studentDao)

    private val _students = MutableStateFlow<List<StudentTrackerTechnical>>(emptyList())
    val students: StateFlow<List<StudentTrackerTechnical>> = _students

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadStudents()
    }

    fun loadStudents() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val apiStudents = repository.getStudentsFromApi()
                repository.clearRoomStudents()
                repository.insertAllStudentsIntoRoom(apiStudents)
                _students.value = repository.getStudentsFromRoom()
            } catch (e: Exception) {
                _errorMessage.value = "Using offline data: ${e.message}"
                _students.value = repository.getStudentsFromRoom()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addStudent(student: StudentTrackerTechnical) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                repository.addStudentToApi(student)
                loadStudents()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add to API: ${e.message}"
                repository.insertStudentIntoRoom(student)
                _students.value = repository.getStudentsFromRoom()
                _isLoading.value = false
            }
        }
    }
}