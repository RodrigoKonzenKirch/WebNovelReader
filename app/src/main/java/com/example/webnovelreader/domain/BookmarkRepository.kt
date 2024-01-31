package com.example.webnovelreader.domain

import com.example.webnovelreader.data.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun saveBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)

    suspend fun allBookmarks(): Flow<List<Bookmark>>

}