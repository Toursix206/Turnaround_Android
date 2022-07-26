package org.android.turnaround.data.repository

import org.android.turnaround.data.remote.datasource.LoginDataSource
import org.android.turnaround.data.remote.datasource.LoginDataSourceImpl
import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.request.PostLoginRequest
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse
import org.android.turnaround.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
) :AuthRepository{
    override suspend fun postLogin(token:String): PostLoginResponse = dataSource.postLogin(token)

    override suspend fun postReLogin(): PostReLoginResponse = dataSource.postReLogin(Token("",""))
}