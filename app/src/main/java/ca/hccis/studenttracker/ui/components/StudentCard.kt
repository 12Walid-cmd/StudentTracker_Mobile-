package ca.hccis.studenttracker.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ca.hccis.studenttracker.entity.StudentTrackerTechnical

@Composable
fun StudentCard(student: StudentTrackerTechnical) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = student.studentName ?: "No Name",
                style = MaterialTheme.typography.titleLarge
            )

            Row {
                AssistChip(
                    onClick = { },
                    label = {
                        Text("Subject: ${student.subject ?: "N/A"}")
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                AssistChip(
                    onClick = { },
                    label = {
                        Text("Score: ${student.studyScore ?: 0}")
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Study Details",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Date: ${student.studyDate ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Method: ${student.studyMethod ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Duration: ${student.studyDurationMinutes ?: 0} minutes",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Daily Goal: ${student.dailyStudyGoal ?: 0} minutes",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Weekly Goal: ${student.weeklyStudyGoal ?: 0} minutes",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    val shareText = """
                        Student Study Summary
                        Name: ${student.studentName ?: "N/A"}
                        Subject: ${student.subject ?: "N/A"}
                        Date: ${student.studyDate ?: "N/A"}
                        Method: ${student.studyMethod ?: "N/A"}
                        Duration: ${student.studyDurationMinutes ?: 0} minutes
                        Daily Goal: ${student.dailyStudyGoal ?: 0} minutes
                        Weekly Goal: ${student.weeklyStudyGoal ?: 0} minutes
                        Score: ${student.studyScore ?: 0}
                    """.trimIndent()

                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Student Study Summary")
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }

                    context.startActivity(
                        Intent.createChooser(shareIntent, "Share via")
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share Summary")
            }
        }
    }
}