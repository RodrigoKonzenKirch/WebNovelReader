package com.example.webnovelreader.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.webnovelreader.domain.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object Keys {
    val currentUrlKey = stringPreferencesKey("current")
}

class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
): UserPreferences {

    override fun currentUrl(): Flow<String> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preference ->
                preference[ Keys.currentUrlKey] ?: ""
            }
    }

    override suspend fun saveCurrentUrl(url: String) {
        dataStore.edit { preference ->
            preference[Keys.currentUrlKey] = url
        }
    }

}