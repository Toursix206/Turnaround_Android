package org.android.turnaround.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.local.LocalPreferencesDataSource
import org.android.turnaround.data.local.LocalPreferencesDataSourceImpl
import org.android.turnaround.data.remote.datasource.LoginDataSource
import org.android.turnaround.data.remote.datasource.LoginDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsLoginDataSource(dataSourceImpl: LoginDataSourceImpl): LoginDataSource

    @Binds
    @Singleton
    fun bindsLocalPreferencesDataSource(localPreferencesDataSource: LocalPreferencesDataSourceImpl):LocalPreferencesDataSource

}