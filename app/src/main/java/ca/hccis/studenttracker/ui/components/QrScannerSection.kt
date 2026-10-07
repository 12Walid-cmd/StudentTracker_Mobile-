package ca.hccis.studenttracker.ui.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun QrScannerSection() {
    val context = LocalContext.current
    var scannedText by remember { mutableStateOf("No QR code scanned yet") }

    val barcodeLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        if (result.contents == null) {
            Toast.makeText(context, "QR scan cancelled", Toast.LENGTH_SHORT).show()
        } else {
            scannedText = result.contents
            Toast.makeText(context, "QR scanned successfully", Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "QR Code Scanner",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Scan a QR code and display the result inside the app.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = {
                    val options = ScanOptions().apply {
                        setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                        setPrompt("Scan a QR code")
                        setBeepEnabled(true)
                        setOrientationLocked(false)
                        setBarcodeImageEnabled(true)
                    }
                    barcodeLauncher.launch(options)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Scan QR Code")
            }

            Text(
                text = "Scanned Result:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = scannedText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

