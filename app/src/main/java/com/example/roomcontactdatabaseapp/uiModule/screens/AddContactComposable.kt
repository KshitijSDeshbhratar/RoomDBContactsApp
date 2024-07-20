package com.example.roomcontactdatabaseapp.uiModule.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomcontactdatabaseapp.dataModule.helpers.ContactsEvent
import com.example.roomcontactdatabaseapp.uiModule.states.ContactsState

@Composable
fun AddContactComposable(
    state: ContactsState,
    onEvent: (ContactsEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(modifier = modifier, onDismissRequest = {
        onEvent(ContactsEvent.HideDialog)
    }, title = { Text(text = "Add Contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(value = state.firstName, onValueChange = {
                    onEvent(ContactsEvent.SetFirstName(it))
                },
                    placeholder = {
                        Text(text = "First Name")
                    })
                TextField(value = state.lastName, onValueChange = {
                    onEvent(ContactsEvent.SetLastName(it))
                },
                    placeholder = {
                        Text(text = "Last Name")
                    })

                TextField(value = state.phoneNumber, onValueChange = {
                    onEvent(ContactsEvent.SetPhoneNumber(it))
                },
                    placeholder = {
                        Text(text = "Phone Number")
                    })
            }
        }, buttons = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(ContactsEvent.SaveContact)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}