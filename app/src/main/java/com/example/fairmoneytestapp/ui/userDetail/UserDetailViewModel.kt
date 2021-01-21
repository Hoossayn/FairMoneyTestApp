package com.example.fairmoneytestapp.ui.userDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.data.sources.repository.Repository

class UserDetailViewModel @ViewModelInject constructor(
    private val repository: Repository
):ViewModel(){

    private var _id = MutableLiveData<String>()

    fun userFromLocal(id: String) : LiveData<User> = repository.getDetailFromLocal(id)


    fun getUserFromRemote(id: String): LiveData<Resource<Unit>>   {

           return repository.getDetailFromRemote(id)

    }

    fun getUserDetail(id: String) {
        _id.value = id
    }


}