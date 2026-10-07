package ca.hccis.studenttracker.util

import android.content.Context
import android.provider.ContactsContract
import ca.hccis.studenttracker.entity.ContactItem

object ContactsHelper {

    fun getContacts(context: Context): List<ContactItem> {
        val contacts = mutableListOf<ContactItem>()

        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val nameIndex =
                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex =
                it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex) ?: "Unknown"
                val phone = it.getString(numberIndex) ?: "No Number"

                contacts.add(ContactItem(name, phone))
            }
        }

        return contacts
    }
}