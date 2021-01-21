package com.example.fairmoneytestapp.data.sources.local

import androidx.lifecycle.LiveData
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.data.sources.local.dao.UserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DummyDatabaseImpl @Inject constructor(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): DummyLocalDatabase {

    override suspend fun saveUsers(user: List<Data>) = withContext(ioDispatcher) {
        userDao.insertAll(user)
    }

    override fun getUsers(): LiveData<List<Data>> = userDao.getAllUsers()

    override suspend fun saveUserDetail(user: User) = withContext(ioDispatcher) {
        userDao.insert(user)
    }

    override fun getUserDetail(id :String) :LiveData<User> = userDao.getUserDetail(id)


}