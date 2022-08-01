package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.BasicResponse
import org.android.turnaround.data.remote.model.Token
import org.android.turnaround.data.remote.model.request.PostLoginRequest
import org.android.turnaround.data.remote.model.request.PostUserSetRequest
import org.android.turnaround.data.remote.model.response.PostLoginResponse
import org.android.turnaround.data.remote.model.response.PostReLoginResponse
import org.android.turnaround.data.remote.model.response.UserSetResponse
import org.android.turnaround.data.remote.service.AuthLoginService
import org.android.turnaround.data.remote.service.UserSetService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val authLoginService: AuthLoginService,
    private val userSetService: UserSetService
) : LoginDataSource {
    override suspend fun postLogin(token: String): BaseResponse<PostLoginResponse> = authLoginService.postLogin(
        PostLoginRequest(SOCIAL_TYPE, token)
    )

    override suspend fun postReLogin(token: Token): BaseResponse<PostReLoginResponse> =
        authLoginService.postReLogin(token)

    override suspend fun isChecked(): BaseResponse<UserSetResponse> =
        userSetService.checkUserSet()

    override suspend fun setUserInfo(body:PostUserSetRequest): BaseResponse<BasicResponse> =
        userSetService.postUserSet(body)


    companion object {
        const val SOCIAL_TYPE = "KAKAO"
    }
}