package com.example.webnovelreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webnovelreader.data.Bookmark
import com.example.webnovelreader.di.DispatcherIo
import com.example.webnovelreader.domain.BookmarkRepository
import com.example.webnovelreader.domain.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkScreenViewModel @Inject constructor(
    private val repository: BookmarkRepository,
    private val userPreference: UserPreferences,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private var _allBookmarksList: StateFlow<List<Bookmark>> = repository.allBookmarks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val allBookmarksList = _allBookmarksList

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch(dispatcherIo) {
            repository.deleteBookmark(bookmark)
        }
    }

    /**
     * Save current url
     */
    fun saveCurrentUrl(url: String){
        viewModelScope.launch(dispatcherIo) {
            userPreference.saveCurrentUrl(url)
        }
    }
}