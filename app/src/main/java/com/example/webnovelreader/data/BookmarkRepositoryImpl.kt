package com.example.webnovelreader.data

import com.example.webnovelreader.domain.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
): BookmarkRepository {
    override suspend fun saveBookmark(bookmark: Bookmark) {
        bookmarkDao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark){
        bookmarkDao.deleteBookmark(bookmark)
    }

    override suspend fun allBookmarks(): Flow<List<Bookmark>> {
        return bookmarkDao.getAllBookmarks()
    }


}