package com.example.roomcontactdatabaseapp.dataModule.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.roomcontactdatabaseapp.dataModule.entities.Contacts
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactsDao {

    @Upsert
    suspend fun upsertContact(contacts: Contacts)

    @Delete
    suspend fun deleteContact(contacts: Contacts)

    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun getAllContactListByFirstName() : Flow<List<Contacts>>

    @Query("SELECT * FROM contacts ORDER BY lastName ASC")
    fun getAllContactsListByLastName() : Flow<List<Contacts>>

    @Query("SELECT * FROM contacts ORDER BY phoneNumber ASC")
    fun getAllContactsListByPhoneNumber() : Flow<List<Contacts>>
}