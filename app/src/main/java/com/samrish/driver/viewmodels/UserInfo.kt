package com.samrish.driver.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.samrish.driver.database.AppDatabase
import com.samrish.driver.database.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {

    private val _currentAssignment: MutableStateFlow<List<User>?> = MutableStateFlow(null)
    val currentAssignment: StateFlow<List<User>?> = _currentAssignment.asStateFlow()
    fun userInfo(context: Context){

        val userList = mutableListOf<User>()

        viewModelScope.launch {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "drishto"
            ).build()

            val userDao = db.userDao()
            userDao.insertUsers(User(1, "Manoj", "Chauhan"))
            userDao.insertUsers(User(2, "Atul", "Chauhan"))
            userDao.insertUsers(User(3, "Ankit", "Chauhan"))
            userDao.insertUsers(User(4, "Krish", "Chauhan"))
            userDao.insertUsers(User(5, "Vaibhav", "Chauhan"))
            userDao.insertUsers(User(6, "Dev", "Chauhan"))
             userList.addAll(userDao.loadUsers())

            Log.d("View Model ", "userInfo: $userList   ")

            Log.d("Unser", "userInfo: ${userList}")
            _currentAssignment.update { assignment ->
                userList
            }

            }
        }

    }


