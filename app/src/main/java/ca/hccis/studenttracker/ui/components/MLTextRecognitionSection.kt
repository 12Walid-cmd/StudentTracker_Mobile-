package ca.hccis.studenttracker.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun MlTextRecognitionSection() {
    val context = LocalContext.current
    var recognizedText by remember { mutableStateOf("No text scanned yet.") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val image = InputImage.fromFilePath(context, uri)
                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        recognizedText = if (visionText.text.isBlank()) {
                            "No text found in the image."
                        } else {
                            visionText.text
                        }
                    }
                    .addOnFailureListener { e ->
                        recognizedText = "Failed to recognize text."
                        Toast.makeText(
                            context,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } catch (e: Exception) {
                recognizedText = "Could not load image."
                Toast.makeText(
                    context,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Machine Learning: Scan Study Notes",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = {
                imagePickerLauncher.launch("image/*")
            }
        ) {
            Text("Pick Note Image")
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = recognizedText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}