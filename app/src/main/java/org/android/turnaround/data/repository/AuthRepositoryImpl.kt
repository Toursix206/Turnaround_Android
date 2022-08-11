package org.android.turnaround.data.repository

import org.android.turnaround.data.local.LocalPreferencesDataSource
import org.android.turnaround.data.remote.datasource.LoginDataSource
import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse
import org.android.turnaround.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource,
    private val localPreferencesDataSource: LocalPreferencesDataSource
) : AuthRepository {
    override suspend fun postLogin(token: String): BaseResponse<PostLoginResponse>  = dataSource.postLogin(token)

        override suspend fun postReLogin(): BaseResponse<PostReLoginResponse> = dataSource.postReLogin(Token("", ""))
        override fun setToken(token:Token){
            localPreferencesDataSource.setAccessToken(token.accessToken)
            localPreferencesDataSource.setRefreshToken(token.refreshToken)
        }

    override fun getToken(): String =
        localPreferencesDataSource.getAccessToken()

    override fun deleteAccessToken() {
        localPreferencesDataSource.deleteAccessToken()
    }


}