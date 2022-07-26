package org.android.turnaround.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.repository.AuthRepositoryImpl
import org.android.turnaround.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsLoginRepository(repository: AuthRepositoryImpl): AuthRepository
}