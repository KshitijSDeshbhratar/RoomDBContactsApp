package com.example.roomcontactdatabaseapp.dataModule.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomcontactdatabaseapp.dataModule.dao.ContactsDao
import com.example.roomcontactdatabaseapp.dataModule.entities.Contacts

@Database(
    entities = [Contacts::class],
    version = 1
)
abstract class ContactsDatabase : RoomDatabase(){
    abstract val contactsDao: ContactsDao
}