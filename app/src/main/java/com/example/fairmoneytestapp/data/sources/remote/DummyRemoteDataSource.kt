package com.example.fairmoneytestapp.data.sources.remote

import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.AllUsers
import com.example.fairmoneytestapp.data.model.entities.User

interface DummyRemoteDataSource  {

    suspend fun getAllUsers(): Resource<AllUsers?>

    suspend fun getUserDetails(id :String): Resource<User?>
}