package com.mvsss.contactsapp.presentation

import com.mvsss.contactsapp.data.local.Contact

sealed class ContactEvent {

    data object SaveContact: ContactEvent()
    data object ShowDialog: ContactEvent()
    data object HideDialog: ContactEvent()

    data class SetFirstName(val firstName: String): ContactEvent()
    data class SetLastName(val lastName: String): ContactEvent()
    data class SetPhoneNumber(val phoneNumber: String): ContactEvent()

    data class DeleteContact(val contact: Contact) : ContactEvent()
    data class SortContacts(val sortType: SortType): ContactEvent()
}