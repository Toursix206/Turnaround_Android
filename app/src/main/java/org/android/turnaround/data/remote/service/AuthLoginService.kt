package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.request.PostLoginRequest
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthLoginService {

    @POST("v1/auth/login")
    suspend fun postLogin(
        @Body body: PostLoginRequest
    ): PostLoginResponse

    @POST("v1/auth/refresh")
    suspend fun postReLogin(
        @Body body: Token
    ): PostReLoginResponse
}