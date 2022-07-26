package org.android.turnaround.domain.repository

import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse

interface AuthRepository {

    suspend fun postLogin(token: String): PostLoginResponse

    suspend fun postReLogin(): PostReLoginResponse
}