package com.mvsss.contactsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("select * from contact order by firstName asc")
     fun getContactsByFirstName() : Flow<List<Contact>>

    @Query("select * from contact order by lastName asc")
     fun getContactsByLastName() : Flow<List<Contact>>

    @Query("select * from contact order by phoneNumber asc")
     fun getContactsByPhoneNumber() : Flow<List<Contact>>

}