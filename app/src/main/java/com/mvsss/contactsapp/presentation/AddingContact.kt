package com.mvsss.contactsapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingContact(state : ContactState, onEvent : (ContactEvent) -> Unit ) {

    
    AlertDialog(onDismissRequest = {onEvent(ContactEvent.HideDialog)},
        ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.firstName,
                onValueChange = { onEvent(ContactEvent.SetFirstName(it)) },
                placeholder = {
                    Text(text = "First Name")
                }
            )

            TextField(
                value = state.lastName,
                onValueChange = { onEvent(ContactEvent.SetLastName(it)) },
                placeholder = {
                    Text(text = "Last Name")
                }
            )

            TextField(
                value = state.phoneNumber,
                onValueChange = { onEvent(ContactEvent.SetPhoneNumber(it)) },
                placeholder = {
                    Text(text = "Phone number")
                }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){

                Button(onClick = { onEvent(ContactEvent.SaveContact) }) {
                    Text(text = "Save Contact")
                }

            }


        }
    }

}