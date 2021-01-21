package com.example.fairmoneytestapp.ui.listUser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.fairmoneytestapp.data.sources.repository.Repository

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
):ViewModel() {

    private val _subjects = MutableLiveData<Unit>()

    fun getSubjects() {
        _subjects.value = Unit
    }

    val fetchingSubject = _subjects.switchMap {
        repository.getAllUsersFromRemote()
    }

    val subjects = repository.getAllUsersFromLocal()
}