package com.example.fairmoneytestapp.data.sources.repository

import androidx.lifecycle.LiveData
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User

interface Repository {

    suspend fun saveAllUsers(data: List<Data>)

    fun getAllUsersFromRemote(): LiveData<Resource<Unit>>

    fun getAllUsersFromLocal(): LiveData<List<Data>>

    suspend fun saveUserDetail(id: String): LiveData<User>

    fun getDetailFromRemote(id:String): LiveData<Resource<Unit>>

    fun getDetailFromLocal(id: String): LiveData<User>
}