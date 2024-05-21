package com.mvsss.contactsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvsss.contactsapp.data.local.Contact
import com.mvsss.contactsapp.data.local.ContactDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsViewModel(private val dao : ContactDao): ViewModel(){

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts = _sortType
        .flatMapLatest {
            when(it){
                SortType.FIRST_NAME -> dao.getContactsByFirstName()
                SortType.LAST_NAME -> dao.getContactsByLastName()
                SortType.PHONE_NUMBER -> dao.getContactsByPhoneNumber()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _contacts, _sortType){ state, contacts, sortType ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),ContactState())


    fun onEvent(event: ContactEvent){

        when(event){

            ContactEvent.SaveContact -> {
                val firstName=  _state.value.firstName
                val lastName =  _state.value.lastName
                val phoneNumber=  _state.value.phoneNumber


                if(firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()){
                    val contact : Contact = Contact(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber
                    )

                    viewModelScope.launch {
                        dao.upsertContact(contact)
                        _state.update {it.copy(
                            firstName = "",
                            lastName = "",
                            phoneNumber = "",
                            isAdding = false
                        )
                        }
                    }

                }
            }
            ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAdding = true
                    )
                }
            }
            ContactEvent.HideDialog ->{
                _state.update {
                    it.copy(
                        isAdding = false
                    )
                }
            }


            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }
            }
            is ContactEvent.SetLastName -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }


            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }
        }

    }


}