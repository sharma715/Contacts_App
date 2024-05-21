package com.mvsss.contactsapp.presentation

import com.mvsss.contactsapp.data.local.Contact

data class ContactState (
    val contacts : List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val sortType: SortType = SortType.FIRST_NAME,
    val isAdding : Boolean = false
)