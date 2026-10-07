package ca.hccis.studenttracker.ui.components

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import ca.hccis.studenttracker.entity.ContactItem
import ca.hccis.studenttracker.util.ContactsHelper

@Composable
fun ContactsSection() {
    val context = LocalContext.current
    var contacts by remember { mutableStateOf<List<ContactItem>>(emptyList()) }
    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        if (granted) {
            contacts = ContactsHelper.getContacts(context).take(5)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Contacts Provider Demo",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = {
                if (permissionGranted) {
                    contacts = ContactsHelper.getContacts(context).take(5)
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
            }
        ) {
            Text("Load Contacts")
        }

        if (!permissionGranted) {
            Text("Permission required to read contacts.")
        }

        contacts.forEach { contact ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = contact.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = contact.phoneNumber,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}