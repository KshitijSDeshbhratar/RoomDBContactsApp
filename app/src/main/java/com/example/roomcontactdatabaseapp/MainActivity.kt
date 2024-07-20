package com.example.roomcontactdatabaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.roomcontactdatabaseapp.dataModule.database.ContactsDatabase
import com.example.roomcontactdatabaseapp.ui.themes.RoomContactDatabaseAppTheme
import com.example.roomcontactdatabaseapp.uiModule.screens.ContactScreen
import com.example.roomcontactdatabaseapp.uiModule.viewModel.ContactsViewModel

class MainActivity : ComponentActivity() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactsDatabase ::class.java,
            "contacts.db"
        ).build()
    }

    val viewModel by viewModels<ContactsViewModel>(
        factoryProducer = {
            object  : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ContactsViewModel(db.contactsDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomContactDatabaseAppTheme {
                val state by viewModel.state.collectAsState()
                ContactScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}
