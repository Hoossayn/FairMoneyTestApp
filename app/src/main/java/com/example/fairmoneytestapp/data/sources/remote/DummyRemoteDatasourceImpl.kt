package com.example.fairmoneytestapp.data.sources.remote

import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.AllUsers
import com.example.fairmoneytestapp.data.model.entities.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DummyRemoteDataSourceImpl @Inject constructor(
    private val dummyService: DummyService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

): DummyRemoteDataSource{
    override suspend fun getAllUsers(): Resource<AllUsers?> = withContext(ioDispatcher) {
        return@withContext try {
            val response = dummyService.getAllUsers()
            if (response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.errorBody().toString(), null)
            }
        }catch (e:Exception){
            Resource.error(e.message!!, null)
        }catch (IO: Exception){
            Resource.error("No internet access", null)

        }
    }

    override suspend fun getUserDetails(id: String): Resource<User?> = withContext(ioDispatcher){
        return@withContext try {
            val response = dummyService.getUserDetails(id)
            if (response.isSuccessful){
                Resource.success(response.body())
            }else{
                Resource.error(response.errorBody().toString(), null)

            }
        }catch (e:Exception){
            Resource.error(e.message!!, null)
        }catch (IO: Exception){
            Resource.error("No internet access", null)

        }
    }
}