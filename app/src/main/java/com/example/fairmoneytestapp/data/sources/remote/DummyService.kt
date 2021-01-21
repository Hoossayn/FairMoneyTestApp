package com.example.fairmoneytestapp.data.sources.remote

import com.example.fairmoneytestapp.data.model.AllUsers
import com.example.fairmoneytestapp.data.model.entities.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val appId = "app-id: 5fff3890b38c6d7224679049"
interface DummyService {


    @Headers(appId)
    @GET("user")
    suspend fun getAllUsers(): Response<AllUsers>

    @Headers(appId)
    @GET("user/{id}")
    suspend fun getUserDetails(@Path("id") id:String): Response<User>
}