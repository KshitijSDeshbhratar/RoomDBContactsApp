package com.example.roomcontactdatabaseapp.uiModule.screens

import android.app.usage.UsageEvents.Event
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomcontactdatabaseapp.dataModule.helpers.ContactsEvent
import com.example.roomcontactdatabaseapp.dataModule.helpers.SortType
import com.example.roomcontactdatabaseapp.uiModule.states.ContactsState

@Composable
fun ContactScreen(state: ContactsState, onEvent: (ContactsEvent) -> Unit) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ContactsEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
            }
        },
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        if (state.issAddingContact) AddContactComposable(state = state, onEvent = onEvent)
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortTypes ->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(ContactsEvent.SortContacts(sortTypes))
                            },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(selected = state.sortTypes == sortTypes, onClick = {
                                onEvent(ContactsEvent.SortContacts(sortTypes))
                            })
                            Text(text = sortTypes.name)
                        }

                    }
                }
            }
            items(state.contactList) { contacts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "${contacts.firstName} ${contacts.lastName}", fontSize = 20.sp)
                        Text(text = "${contacts.phoneNumber}", fontSize = 12.sp)

                    }
                    IconButton(onClick = {
                        onEvent(ContactsEvent.DeleteContacts(contact = contacts))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Contact"
                        )
                    }
                }
            }
        }

    }

}