package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.BasicResponse
import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.request.PostUserSetRequest
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse
import org.android.turnaround.data.remote.model.response.UserSetResponse

interface LoginDataSource {

    suspend fun postLogin(token: String): BaseResponse<PostLoginResponse>

    suspend fun postReLogin(token: Token): BaseResponse<PostReLoginResponse>

    suspend fun isChecked() :BaseResponse<UserSetResponse>

    suspend fun setUserInfo(body: PostUserSetRequest) :BaseResponse<BasicResponse>
}