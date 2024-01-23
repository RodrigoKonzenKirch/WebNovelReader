package com.example.webnovelreader.di

import com.example.webnovelreader.data.UserDataStore
import com.example.webnovelreader.domain.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class UserPreferenceModule {

    @Binds
    abstract fun bindUserPreferences(impl: UserDataStore): UserPreferences
}