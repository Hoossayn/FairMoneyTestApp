package com.example.fairmoneytestapp.ui.listUser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fairmoneytestapp.data.FakeRepository
import com.example.fairmoneytestapp.data.helper.Resource
import com.example.fairmoneytestapp.util.TestObjectUtil
import com.example.ulesson.util.MainCoroutineRule
import com.example.ulesson.util.getOrAwaitValue
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
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    private val repository = FakeRepository()

    @Before
    fun setup() {
        mainViewModel = MainViewModel(repository)

    }

    @Test
    fun `assert that call to network passes`() {
        mainCoroutine.runBlockingTest {
            mainViewModel.getSubjects()

            val status = mainViewModel.fetchingSubject.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.success(Unit)))
        }
    }

    @Test
    fun `assert that error response is received when error occurs calling network`() {
        mainCoroutine.runBlockingTest {
            repository.setShouldReturnError(true)
            mainViewModel.getSubjects()

            val status = mainViewModel.fetchingSubject.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.error("error occurred")))
        }
    }

    @Test
    fun `assert that call to network saves subject data`() {
        mainCoroutine.runBlockingTest {
            mainViewModel.getSubjects()

            val status = mainViewModel.fetchingSubject.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.success(Unit)))

            val subjects = mainViewModel.subjects.getOrAwaitValue()
            MatcherAssert.assertThat(subjects, `is`(TestObjectUtil.data))
        }
    }
}