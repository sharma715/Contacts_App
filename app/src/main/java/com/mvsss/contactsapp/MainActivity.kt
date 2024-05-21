package com.mvsss.contactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mvsss.contactsapp.data.local.ContactDatabase
import com.mvsss.contactsapp.ui.theme.ContactsAppTheme
import com.mvsss.contactsapp.presentation.ContactScreen
import com.mvsss.contactsapp.presentation.ContactsViewModel

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contacts_db"
        ).build()
    }

    private val viewModel by viewModels<ContactsViewModel>(factoryProducer={
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ContactsViewModel(db.dao) as T
            }
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsAppTheme {

                val state by  viewModel.state.collectAsState()
                ContactScreen(state = state, onEvent = viewModel::onEvent )
            }
        }
    }
}
