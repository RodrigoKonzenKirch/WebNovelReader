package com.example.webnovelreader.viewmodels

import com.example.webnovelreader.MainCoroutineRule
import com.example.webnovelreader.data.Bookmark
import com.example.webnovelreader.domain.BookmarkRepository
import com.example.webnovelreader.domain.UserPreferences
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkScreenViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val mockUserPreferences = mockk<UserPreferences>(relaxed = true)
    private val mockRepository = mockk<BookmarkRepository>(relaxed = true)
    private lateinit var viewModel: BookmarkScreenViewModel

    private val myTestScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(myTestScheduler)

    @Before
    fun setUp() {
        viewModel = BookmarkScreenViewModel(mockRepository, mockUserPreferences, testDispatcher)
    }

    @Test
    fun `saveCurrentUrl saves url to preferences`(){
        val url = "https://www.example.com"
        viewModel.saveCurrentUrl(url)

        coVerify{ mockUserPreferences.saveCurrentUrl(url) }
    }

    @Test
    fun `test delete bookmark`() {
        val bookmark = Bookmark(0, "name", "https://www.example.com")

        viewModel.deleteBookmark(bookmark)

        coVerify { mockRepository.deleteBookmark(bookmark) }
    }


}