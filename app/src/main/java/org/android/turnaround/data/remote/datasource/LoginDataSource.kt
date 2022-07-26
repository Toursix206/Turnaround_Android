package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse

interface LoginDataSource {

    suspend fun postLogin(token: String): PostLoginResponse

    suspend fun postReLogin(token: Token): PostReLoginResponse
}