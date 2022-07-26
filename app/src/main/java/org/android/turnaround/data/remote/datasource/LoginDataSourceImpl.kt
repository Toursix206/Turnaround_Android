package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.request.PostLoginRequest
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse
import org.android.turnaround.data.remote.service.AuthLoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val authLoginService: AuthLoginService
) : LoginDataSource {
    override suspend fun postLogin(token: String): PostLoginResponse = authLoginService.postLogin(
        PostLoginRequest(SOCIAL_TYPE, "token")
    )

    override suspend fun postReLogin(token: Token): PostReLoginResponse =
        authLoginService.postReLogin(token)


    companion object {
        const val SOCIAL_TYPE = "KAKAO"
    }
}