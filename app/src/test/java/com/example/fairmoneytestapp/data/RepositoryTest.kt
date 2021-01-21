package com.example.fairmoneytestapp.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.data.sources.local.DummyLocalDatabase
import com.example.fairmoneytestapp.data.sources.remote.DummyRemoteDataSource
import com.example.fairmoneytestapp.data.sources.repository.RepositoryImpl
import com.example.fairmoneytestapp.util.TestObjectUtil
import com.example.ulesson.util.*
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class RepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: RepositoryImpl
    private val remoteDataSource = mock(DummyRemoteDataSource::class.java)
    private val localDataSource = mock(DummyLocalDatabase::class.java)

    @Before
    fun setup() {
        repository = RepositoryImpl(remoteDataSource, localDataSource, Dispatchers.Main)
    }

    @Test
    fun `assert that call to network returns success`() = mainCoroutineRule.runBlockingTest {

        `when`(remoteDataSource.getAllUsers()).thenReturn(Resource.success(TestObjectUtil.subjectDataResponse))

        val response = repository.getAllUsersFromRemote().getOrAwaitValue()

        verify(remoteDataSource).getAllUsers()
        assertThat(response, `is`(Resource.success(Unit)))
    }

    @Test
    fun `assert that when call to network fails it returns the appropriate error message`() =
        mainCoroutineRule.runBlockingTest {
            `when`(remoteDataSource.getAllUsers()).thenReturn(Resource.error("error occurred"))

            val response = repository.getAllUsersFromRemote().getOrAwaitValue()

            verify(remoteDataSource).getAllUsers()
            assertThat(response, `is`(Resource.error("error occurred")))
            verifyNoMoreInteractions(remoteDataSource, localDataSource)
        }

    //verify data passed using argument captor
    @Test
    fun `assert that when call to network is successful it should also persist the data gotten`() =
        mainCoroutineRule.runBlockingTest {
            `when`(remoteDataSource.getAllUsers()).thenReturn(Resource.success(TestObjectUtil.subjectDataResponse))
            `when`(localDataSource.saveUsers(TestObjectUtil.data)).thenReturn(Unit)

            val response = repository.getAllUsersFromRemote().getOrAwaitValue()

            assertThat(response, `is`(Resource.success(Unit)))
            verify(remoteDataSource).getAllUsers()
            verify(localDataSource).saveUsers(TestObjectUtil.data)
        }

    @Test
    fun `assert that repository gets data`() {
        val data = MutableLiveData<List<Data>>()
        data.value = TestObjectUtil.data

        `when`(localDataSource.getUsers()).thenReturn(data)

        val response = repository.getAllUsersFromLocal().getOrAwaitValue()
        verify(localDataSource).getUsers()
        assertThat(response, `is`(TestObjectUtil.data))
    }


    @Test
    fun `assert that repository saves data`() = mainCoroutineRule.runBlockingTest {
        val argumentCapture = argumentCaptor<List<Data>>()

        repository.saveAllUsers(TestObjectUtil.data)
        verify(localDataSource).saveUsers(argumentCapture.capture())

        val subjects = argumentCapture.firstValue
        assertThat(subjects, `is`(TestObjectUtil.data))
    }


    @Test
    fun `assert that repository get userDetail`(){
        val data = MutableLiveData<User>()
        val id = argumentCaptor<String>()

        data.value = TestObjectUtil.user

        `when`(localDataSource.getUserDetail(id.toString())).thenReturn(data)

        val response = repository.getDetailFromLocal(id.capture()).getOrAwaitValue()
        verify(localDataSource).getUserDetail(id.toString())
        assertThat(response, `is`(TestObjectUtil.users))
    }


    @Test
    fun `assert that repository saves userDetail`() = mainCoroutineRule.runBlockingTest{

        val id = argumentCaptor<String>()
        val user = argumentCaptor<User>()

        repository.saveUserDetail(id.toString())
        verify(localDataSource).saveUserDetail(TestObjectUtil.user)

        val subjects = user.firstValue
        assertThat(subjects, `is`(TestObjectUtil.user.dateOfBirth))

    }

}