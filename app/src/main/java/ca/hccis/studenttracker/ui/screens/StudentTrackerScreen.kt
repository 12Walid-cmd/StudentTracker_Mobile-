package ca.hccis.studenttracker.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.hccis.studenttracker.bo.StudentTrackerBo
import ca.hccis.studenttracker.broadcast.receiver.StudyReminderReceiver
import ca.hccis.studenttracker.entity.StudentTrackerTechnical
import ca.hccis.studenttracker.service.StudyTimerService
import ca.hccis.studenttracker.ui.components.ContactsSection
import ca.hccis.studenttracker.ui.components.MlTextRecognitionSection
import ca.hccis.studenttracker.ui.components.StudentCard
import ca.hccis.studenttracker.ui.components.StudentFormSection
import ca.hccis.studenttracker.ui.components.StudyImageCarousel
import ca.hccis.studenttracker.util.CisUtility
import ca.hccis.studenttracker.util.PreferenceHelper
import ca.hccis.studenttracker.viewmodel.StudentViewModel
import ca.hccis.studenttracker.ui.components.QrScannerSection

@Composable
fun StudentTrackerScreen(
    modifier: Modifier = Modifier,
    studentViewModel: StudentViewModel = viewModel()
) {
    val context = LocalContext.current
    val preferenceHelper = remember { PreferenceHelper(context) }
    val snackbarHostState = remember { SnackbarHostState() }

    var idInput by rememberSaveable { mutableStateOf("") }
    var studentName by rememberSaveable { mutableStateOf("") }
    var subject by rememberSaveable { mutableStateOf("") }
    var studyDate by rememberSaveable { mutableStateOf(CisUtility.getTodayString("yyyy-MM-dd")) }
    var studyMethod by rememberSaveable { mutableStateOf("Counting") }
    var studyDurationMinutes by rememberSaveable { mutableStateOf("20") }
    var dailyStudyGoal by rememberSaveable { mutableStateOf("") }
    var weeklyStudyGoal by rememberSaveable { mutableStateOf("") }

    val students by studentViewModel.students.collectAsStateWithLifecycle()
    val isLoading by studentViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by studentViewModel.errorMessage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        studentName = preferenceHelper.getLastStudentName()
        subject = preferenceHelper.getLastSubject()
        dailyStudyGoal = preferenceHelper.getDailyGoal()
        weeklyStudyGoal = preferenceHelper.getWeeklyGoal()
        studentViewModel.loadStudents()
    }

    val totalSessions = students.size
    val totalMinutes = students.sumOf { it.studyDurationMinutes ?: 0 }
    val averageScore = if (students.isNotEmpty()) {
        students.map { it.studyScore ?: 0 }.average()
    } else {
        0.0
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp + innerPadding.calculateTopPadding(),
                bottom = 16.dp + innerPadding.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Student Study Tracker",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = "Track study sessions, calculate study scores, manage progress, and stay motivated.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Quick Summary",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text("Saved Sessions: $totalSessions")
                        Text("Total Study Minutes: $totalMinutes")
                        Text("Average Score: %.1f".format(averageScore))
                    }
                }
            }

            item {
                StudyImageCarousel()
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Study Reminders",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Button(
                            onClick = {
                                val intent = Intent(context, StudyReminderReceiver::class.java).apply {
                                    action = "ca.hccis.studenttracker.STUDY_REMINDER"
                                }
                                context.sendBroadcast(intent)
                                Toast.makeText(
                                    context,
                                    "Custom broadcast sent",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Test Reminder")
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    androidx.compose.foundation.layout.Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Study Timer Service",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Button(
                            onClick = {
                                val serviceIntent = Intent(context, StudyTimerService::class.java)
                                ContextCompat.startForegroundService(context, serviceIntent)
                                Toast.makeText(
                                    context,
                                    "Study timer service started",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Study Timer Service")
                        }

                        Button(
                            onClick = {
                                val serviceIntent = Intent(context, StudyTimerService::class.java)
                                context.stopService(serviceIntent)
                                Toast.makeText(
                                    context,
                                    "Study timer service stopped",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Stop Study Timer Service")
                        }
                    }
                }
            }

            item {
                ContactsSection()
            }

            item {
                MlTextRecognitionSection()
            }

            item {
                StudentFormSection(
                    idInput = idInput,
                    onIdInputChange = { idInput = it },
                    studentName = studentName,
                    onStudentNameChange = { studentName = it },
                    subject = subject,
                    onSubjectChange = { subject = it },
                    studyDate = studyDate,
                    onStudyDateChange = { studyDate = it },
                    studyMethod = studyMethod,
                    onStudyMethodChange = { studyMethod = it },
                    studyDurationMinutes = studyDurationMinutes,
                    onStudentDurationMinutesChange = { studyDurationMinutes = it },
                    dailyStudyGoal = dailyStudyGoal,
                    onDailyStudyGoalChange = { dailyStudyGoal = it },
                    weeklyStudyGoal = weeklyStudyGoal,
                    onWeeklyStudyGoalChange = { weeklyStudyGoal = it },
                    onCalculateClick = {
                        val student = StudentTrackerTechnical(
                            studyDurationMinutes = studyDurationMinutes.toIntOrNull(),
                            id = idInput.toIntOrNull(),
                            studentName = studentName,
                            subject = subject,
                            studyDate = studyDate,
                            studyMethod = studyMethod,
                            dailyStudyGoal = dailyStudyGoal.toIntOrNull(),
                            weeklyStudyGoal = weeklyStudyGoal.toIntOrNull(),
                            notes = null
                        )

                        StudentTrackerBo.calculateStudyScore(student)
                        studentViewModel.addStudent(student)

                        preferenceHelper.saveLastStudentName(studentName)
                        preferenceHelper.saveLastSubject(subject)
                        preferenceHelper.saveDailyGoal(dailyStudyGoal)
                        preferenceHelper.saveWeeklyGoal(weeklyStudyGoal)

                        Toast.makeText(
                            context,
                            "Study session saved",
                            Toast.LENGTH_SHORT
                        ).show()

                        idInput = ""
                        studentName = ""
                        subject = ""
                        studyMethod = ""
                        studyDurationMinutes = ""
                        dailyStudyGoal = ""
                        weeklyStudyGoal = ""
                        studyDate = CisUtility.getTodayString("yyyy-MM-dd")

                        studentViewModel.loadStudents()
                    },
                    onClearClick = {
                        idInput = ""
                        studentName = ""
                        subject = ""
                        studyMethod = ""
                        studyDurationMinutes = ""
                        dailyStudyGoal = ""
                        weeklyStudyGoal = ""
                        studyDate = CisUtility.getTodayString("yyyy-MM-dd")
                    }
                )
            }

            if (isLoading) {
                item {
                    CircularProgressIndicator()
                }
            }

            if (errorMessage != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        androidx.compose.foundation.layout.Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Connection Notice",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )

                            Text(
                                text = "Unable to connect to the server. Displaying locally saved data instead.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            if (students.isNotEmpty()) {
                item {
                    Text(




                        text = "Saved Study Sessions",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(students) { student ->
                    StudentCard(student = student)
                }
            } else {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        androidx.compose.foundation.layout.Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "No study sessions saved yet",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Add a new study session above to start tracking progress.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

            }
            item {
                QrScannerSection()
            }
        }
    }

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrBlank()) {
            snackbarHostState.showSnackbar("Error: $errorMessage")
        }
    }
}