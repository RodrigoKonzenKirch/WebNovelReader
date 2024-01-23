package com.example.webnovelreader.domain

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    /**
     * Returns current url flow
     * */
    fun currentUrl(): Flow<String>

    /**
     * Saves current url in data store
     */
    suspend fun saveCurrentUrl(url: String)


}