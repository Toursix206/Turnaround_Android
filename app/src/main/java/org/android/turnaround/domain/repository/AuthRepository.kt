package org.android.turnaround.domain.repository

import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse

interface AuthRepository {

    suspend fun postLogin(token: String): BaseResponse<PostLoginResponse>

    suspend fun postReLogin(): BaseResponse<PostReLoginResponse>

    fun setToken(token:Token)

    fun getToken():String

    fun deleteAccessToken()
}