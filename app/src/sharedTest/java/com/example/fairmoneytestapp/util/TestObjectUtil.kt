package com.example.fairmoneytestapp.util

import com.example.fairmoneytestapp.data.model.AllUsers
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User

object TestObjectUtil {

    val user = User("09-09-1999","email1@email.com","user1","M","1","user1","09099090909","image1.png","09-09-2020","mr")

    val users = listOf(
        User("09-09-1999","email1@email.com","user1","M","1","user1","09099090909","image1.png","09-09-2020","mr"),
        User("09-09-1999","email1@email.com","user1","M","1","user1","09099090909","image1.png","09-09-2020","mr"),
        User("09-09-1999","email1@email.com","user1","M","1","user1","09099090909","image1.png","09-09-2020","mr"),
        User("09-09-1999","email1@email.com","user1","M","1","user1","09099090909","image1.png","09-09-2020","mr"),
        User("09-09-1999","email1@email.com","user1","M","1","user1","09099090909","image1.png","09-09-2020","mr")
    )


    val data =  listOf(
        Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs"),
        Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs"),
        Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs"),
        Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs")
    )


    val subjectDataResponse = AllUsers(
        listOf(
            Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs"),
            Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs"),
            Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs"),
            Data("sateHughes@email.com","Kate","01","Hughes","image1.png","mrs")
        )
    )
}