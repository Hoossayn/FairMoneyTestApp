package com.example.fairmoneytestapp.data.sources.local

import androidx.lifecycle.LiveData
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User

interface DummyLocalDatabase {

    suspend fun saveUsers(users: List<Data>)
    fun getUsers(): LiveData<List<Data>>

    suspend fun saveUserDetail(user: User)
    fun getUserDetail(id :String): LiveData<User>
}