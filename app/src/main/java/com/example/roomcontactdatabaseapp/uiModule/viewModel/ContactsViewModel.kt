package com.example.roomcontactdatabaseapp.uiModule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcontactdatabaseapp.dataModule.dao.ContactsDao
import com.example.roomcontactdatabaseapp.dataModule.entities.Contacts
import com.example.roomcontactdatabaseapp.dataModule.helpers.ContactsEvent
import com.example.roomcontactdatabaseapp.dataModule.helpers.SortType
import com.example.roomcontactdatabaseapp.uiModule.states.ContactsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactsViewModel(val dao: ContactsDao) : ViewModel() {

    private val _sortTypes = MutableStateFlow(SortType.FIRST_NAME)
    private val _state = MutableStateFlow(ContactsState())
    private val _contacts = _sortTypes.flatMapLatest { sortType ->
        when (sortType) {
            SortType.FIRST_NAME -> dao.getAllContactListByFirstName()
            SortType.LAST_NAME -> dao.getAllContactsListByLastName()
            SortType.PHONE_NUMBER -> dao.getAllContactsListByPhoneNumber()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val state = combine(_state, _sortTypes, _contacts) { state, sortType, contact ->
        state.copy(
            contactList = contact,
            sortTypes = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactsState())

    fun onEvent(event: ContactsEvent) {
        viewModelScope.launch {
            when (event) {
                is ContactsEvent.DeleteContacts -> {
                    dao.deleteContact(event.contact)
                }
                ContactsEvent.HideDialog -> {
                    _state.update {
                        it.copy(
                            issAddingContact = false
                        )
                    }
                }
                ContactsEvent.SaveContact -> {
                    val firstName = state.value.firstName
                    val lastName = state.value.lastName
                    val phoneNumber = state.value.phoneNumber
                    if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                        return@launch
                    }
                    val contact = Contacts(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber
                    )
                    viewModelScope.launch {
                        dao.upsertContact(contacts = contact)
                    }
                    _state.update {
                        it.copy(
                            issAddingContact = false,
                            firstName = "",
                            lastName = "",
                            phoneNumber = ""
                        )
                    }
                }
                is ContactsEvent.SetFirstName -> {
                    _state.update {
                        it.copy(
                            firstName = event.firstName
                        )
                    }
                }
                is ContactsEvent.SetLastName -> {
                    _state.update {
                        it.copy(
                            lastName = event.lastName
                        )
                    }
                }
                is ContactsEvent.SetPhoneNumber -> {
                    _state.update {
                        it.copy(
                            phoneNumber = event.phoneNumber
                        )
                    }
                }
                ContactsEvent.ShowDialog -> {
                    _state.update {
                        it.copy(
                            issAddingContact = true
                        )
                    }
                }
                is ContactsEvent.SortContacts -> {
                    _sortTypes.value = event.sortType
                }
            }
        }
    }
}