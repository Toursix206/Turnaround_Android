package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.service.AuthLoginService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthLoginService =
        retrofit.create(AuthLoginService::class.java)
}