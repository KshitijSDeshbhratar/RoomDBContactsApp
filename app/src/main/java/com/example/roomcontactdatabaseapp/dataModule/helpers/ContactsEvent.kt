package com.example.roomcontactdatabaseapp.dataModule.helpers

import com.example.roomcontactdatabaseapp.dataModule.entities.Contacts

sealed interface ContactsEvent {

    object SaveContact : ContactsEvent
    data class SetFirstName(val firstName: String) : ContactsEvent
    data class SetLastName(val lastName: String) : ContactsEvent
    data class SetPhoneNumber(val phoneNumber: String) : ContactsEvent
    object ShowDialog : ContactsEvent
    object HideDialog : ContactsEvent
    data class SortContacts(val sortType: SortType) : ContactsEvent
    data class DeleteContacts(val contact: Contacts) : ContactsEvent
}