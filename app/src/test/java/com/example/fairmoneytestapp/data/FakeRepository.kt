package com.example.fairmoneytestapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.helper.Resource.Status
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.data.sources.repository.Repository
import com.example.fairmoneytestapp.util.TestObjectUtil

class FakeRepository : Repository {

    private var shouldReturnError = false

    private val data = mutableListOf<Data>()
    private val user = TestObjectUtil.user

    private val observeData = MutableLiveData<List<Data>>()
    private val observeUser = MutableLiveData<User>()

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }


    private fun refreshData() {
        observeData.value = data
        observeUser.value = user
    }

    override suspend fun saveAllUsers(data: List<Data>) {
        this.data.addAll(data)
        refreshData()
    }

    override fun getAllUsersFromRemote(): LiveData<Resource<Unit>> {
        return liveData {
            val response = if (shouldReturnError) {
                Resource.error("error occurred", null)
            } else {
                Resource.success(TestObjectUtil.data)
            }

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        saveAllUsers(it)
                    }
                    emit(Resource.success(Unit))
                }
                Status.ERROR -> {
                    emit(Resource.error(response.message!!, null))
                }
                else -> {
                }
            }
        }
    }

    override fun getAllUsersFromLocal(): LiveData<List<Data>> {
        return observeData
    }

    override suspend fun saveUserDetail(id: String): LiveData<User> {
        return  observeUser
    }

    override fun getDetailFromRemote(id: String): LiveData<Resource<Unit>> {
        return liveData {
            val response = if (shouldReturnError) {
                Resource.error("error occurred", null)
            } else {
                Resource.success(TestObjectUtil.data)
            }

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        saveUserDetail(id)
                    }
                    emit(Resource.success(Unit))
                }
                Status.ERROR -> {
                    emit(Resource.error(response.message!!, null))
                }
                else -> {
                }
            }
        }
    }

    override fun getDetailFromLocal(id: String): LiveData<User> {
        return  observeUser
    }

}