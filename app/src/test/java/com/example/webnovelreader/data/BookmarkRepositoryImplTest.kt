package com.example.webnovelreader.data

import com.example.webnovelreader.MainCoroutineRule
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkRepositoryImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val bookmarkDaoMock = mockk<BookmarkDao>(relaxed = true)
    private lateinit var bookmarkRepositoryImpl: BookmarkRepositoryImpl

    @Before
    fun setUp() {
        bookmarkRepositoryImpl = BookmarkRepositoryImpl(bookmarkDaoMock)
    }

    @Test
    fun `save Bookmark`() = runTest {
        val bookmarkSample = Bookmark(0, "name", "www.example.com")

        bookmarkRepositoryImpl.saveBookmark(bookmarkSample)

        coVerify { bookmarkDaoMock.insertBookmark(bookmarkSample) }
    }

    @Test
    fun `delete Bookmark`() = runTest {
        val bookmarkSample = Bookmark(0, "name", "www.example.com")

        bookmarkRepositoryImpl.deleteBookmark(bookmarkSample)

        coVerify { bookmarkDaoMock.deleteBookmark(bookmarkSample) }
    }

    @Test
    fun `get all Bookmarks`() = runTest {
        bookmarkRepositoryImpl.allBookmarks()

        coVerify { bookmarkDaoMock.getAllBookmarks() }
    }
}