package com.example.webnovelreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webnovelreader.di.DispatcherIo
import com.example.webnovelreader.domain.BookmarkRepository
import com.example.webnovelreader.domain.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: BookmarkRepository,
    private val userPreference: UserPreferences,
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
): ViewModel() {

    /**
     * Url flow to observe the current url
     */
    val currentUrl: StateFlow<String> = userPreference.currentUrl().filter {
        it.isNotEmpty()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            "No Url"
        )

    /**
     * Save current url
     */
    fun saveCurrentUrl(url: String){
        viewModelScope.launch(dispatcherIo) {
            userPreference.saveCurrentUrl(url)
        }
    }

}