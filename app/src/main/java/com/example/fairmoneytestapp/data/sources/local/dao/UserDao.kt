package com.example.fairmoneytestapp.data.sources.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM characters")
    fun getAllUsers(): LiveData<List<Data>>

    @Query("SELECT * FROM characters_detail WHERE id = :id")
    fun getUserDetail(id: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<Data>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete
    fun deleteAll(movie: List<Data>)

    @Delete
    fun delete(user:User)

}