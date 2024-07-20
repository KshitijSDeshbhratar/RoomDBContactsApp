package com.example.roomcontactdatabaseapp.uiModule.states

import com.example.roomcontactdatabaseapp.dataModule.entities.Contacts
import com.example.roomcontactdatabaseapp.dataModule.helpers.SortType

data class ContactsState(
    val contactList: List<Contacts> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val issAddingContact: Boolean = false,
    val sortTypes: SortType = SortType.FIRST_NAME
)
