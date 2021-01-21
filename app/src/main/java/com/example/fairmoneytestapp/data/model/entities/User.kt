package com.example.fairmoneytestapp.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_detail")
data class User(
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val gender: String,
    @PrimaryKey
    val id: String,
    val lastName: String,
    val phone: String,
    val picture: String,
    val registerDate: String,
    val title: String
)