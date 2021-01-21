package com.example.fairmoneytestapp.data.sources.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.helper.Resource.Status
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.data.sources.local.DummyLocalDatabase
import com.example.fairmoneytestapp.data.sources.remote.DummyRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: DummyRemoteDataSource,
    private val localDataSource: DummyLocalDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): Repository {

    override suspend fun saveAllUsers(data: List<Data>) {
        localDataSource.saveUsers(data)
    }

    override fun getAllUsersFromRemote(): LiveData<Resource<Unit>> = liveData(ioDispatcher){
        val response = remoteDataSource.getAllUsers()
        when(response.status){
            Status.LOADING -> {
                emit(Resource.loading((Unit)))
            }

            Status.SUCCESS -> {
                response.data.let {
                    localDataSource.saveUsers(it?.data!!)
                }
                emit(Resource.success(Unit))
            }

            Status.ERROR -> {
                emit(Resource.error(response.message!!, null))
            }
        }
    }

    override fun getAllUsersFromLocal(): LiveData<List<Data>> = localDataSource.getUsers()


    override suspend fun saveUserDetail(id: String): LiveData<User> {
        return  localDataSource.getUserDetail(id)
    }

    override fun getDetailFromRemote(id: String): LiveData<Resource<Unit>> = liveData(ioDispatcher) {
        val response = remoteDataSource.getUserDetails(id)
        when(response.status){
            Status.LOADING -> {
                emit(Resource.loading((Unit)))
            }

            Status.SUCCESS -> {
                response.data.let {
                    localDataSource.saveUserDetail(it!!)
                }
                emit(Resource.success(Unit))
            }

            Status.ERROR -> {
                emit(Resource.error(response.message!!, null))
            }
        }
    }

    override fun getDetailFromLocal(id: String): LiveData<User> {
        return localDataSource.getUserDetail(id)
    }


}