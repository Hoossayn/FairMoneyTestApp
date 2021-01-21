package com.example.fairmoneytestapp.ui.userDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fairmoneytestapp.data.FakeRepository
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.ui.listUser.MainViewModel
import com.example.fairmoneytestapp.util.TestObjectUtil
import com.example.ulesson.util.MainCoroutineRule
import com.example.ulesson.util.getOrAwaitValue
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor.*


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    private lateinit var viewModel: UserDetailViewModel

    private val repository = FakeRepository()


    @Before
    fun setup() {
        viewModel = UserDetailViewModel(repository)
    }

    @Test
    fun `assert that call to network passes`() {
        val id = argumentCaptor<String>()

        mainCoroutine.runBlockingTest {

            val status = viewModel.getUserFromRemote(id.toString()).getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.success(Unit)))
        }
    }

    @Test
    fun `assert that error response is received when error occurs calling network`() {
        val id = argumentCaptor<String>()
        mainCoroutine.runBlockingTest {
            repository.setShouldReturnError(true)

            val status = viewModel.getUserFromRemote(id.toString()).getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.error("error occurred")))
        }
    }

    @Test
    fun `assert that call to network save users detail`() {
        val id = argumentCaptor<String>()

        mainCoroutine.runBlockingTest {

            val status = viewModel.getUserFromRemote(id.toString()).getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.success(Unit)))

            val subjects = viewModel.userFromLocal(id.toString()).getOrAwaitValue()
            MatcherAssert.assertThat(subjects, `is`(TestObjectUtil.user))
        }
    }
}