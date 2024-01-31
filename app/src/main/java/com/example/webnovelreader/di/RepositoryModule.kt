package com.example.webnovelreader.di

import com.example.webnovelreader.data.BookmarkRepositoryImpl
import com.example.webnovelreader.domain.BookmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideBookmarkRepository(repository: BookmarkRepositoryImpl): BookmarkRepository


}