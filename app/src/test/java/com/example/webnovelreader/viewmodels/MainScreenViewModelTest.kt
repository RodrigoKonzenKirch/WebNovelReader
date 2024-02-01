package com.example.webnovelreader.viewmodels

import com.example.webnovelreader.MainCoroutineRule
import com.example.webnovelreader.domain.BookmarkRepository
import com.example.webnovelreader.domain.UserPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val mockUserPreferences = mockk<UserPreferences>(relaxed = true)
    private val mockRepository = mockk<BookmarkRepository>(relaxed = true)
    private lateinit var viewModel: MainScreenViewModel

    private val myTestScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(myTestScheduler)


    @Before
    fun setUp(){
        viewModel = MainScreenViewModel(mockRepository, mockUserPreferences, testDispatcher)
    }

    @Test
    fun `currentUrl returns empty url when no url stored`(){

        val expectedValue = "No Url"
        every { mockUserPreferences.currentUrl() } returns flowOf("")

        assertThat(viewModel.currentUrl.value).isEqualTo(expectedValue)
    }

    @Test
    fun `saveCurrentUrl saves url to preferences`(){
        val url = "https://www.example.com"
        viewModel.saveCurrentUrl(url)

        coVerify{ mockUserPreferences.saveCurrentUrl(url) }
    }

}