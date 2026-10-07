package ca.hccis.studenttracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun StudentFormSection(
    idInput: String,
    onIdInputChange: (String) -> Unit,
    studentName: String,
    onStudentNameChange: (String) -> Unit,
    subject: String,
    onSubjectChange: (String) -> Unit,
    studyDate: String,
    onStudyDateChange: (String) -> Unit,
    studyMethod: String,
    onStudyMethodChange: (String) -> Unit,
    studyDurationMinutes: String,
    onStudentDurationMinutesChange: (String) -> Unit,
    dailyStudyGoal: String,
    onDailyStudyGoalChange: (String) -> Unit,
    weeklyStudyGoal: String,
    onWeeklyStudyGoalChange: (String) -> Unit,
    onCalculateClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Add Study Session",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Enter the study details below to calculate and save a study score.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = idInput,
                onValueChange = onIdInputChange,
                label = { Text("Student ID") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = studentName,
                onValueChange = onStudentNameChange,
                label = { Text("Student Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = subject,
                onValueChange = onSubjectChange,
                label = { Text("Subject") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = studyDate,
                onValueChange = onStudyDateChange,
                label = { Text("Study Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = studyMethod,
                onValueChange = onStudyMethodChange,
                label = { Text("Study Method") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = studyDurationMinutes,
                onValueChange = onStudentDurationMinutesChange,
                label = { Text("Study Duration (Minutes)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = dailyStudyGoal,
                onValueChange = onDailyStudyGoalChange,
                label = { Text("Daily Goal (Minutes)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = weeklyStudyGoal,
                onValueChange = onWeeklyStudyGoalChange,
                label = { Text("Weekly Goal (Minutes)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Button(
                onClick = onCalculateClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate and Save")
            }

            TextButton(
                onClick = onClearClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Form")
            }
        }
    }
}