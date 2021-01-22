package com.example.fairmoneytestapp.ui.listUser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.fairmoneytestapp.data.sources.repository.Repository

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
):ViewModel() {

    private val _userList = MutableLiveData<Unit>()

    fun getAllUsers() {
        _userList.value = Unit
    }

    val fetchAllUsers = _userList.switchMap {
        repository.getAllUsersFromRemote()
    }

    val userList = repository.getAllUsersFromLocal()
}