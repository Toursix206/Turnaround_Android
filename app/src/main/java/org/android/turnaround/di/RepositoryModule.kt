package org.android.turnaround.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.repository.AuthRepositoryImpl
import org.android.turnaround.data.repository.UserSetRepositoryImpl
import org.android.turnaround.domain.repository.AuthRepository
import org.android.turnaround.domain.repository.UserSetRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsLoginRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindsUserSetRepository(repository: UserSetRepositoryImpl):UserSetRepository
}