package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.BasicResponse
import org.android.turnaround.data.remote.model.request.PostUserSetRequest
import org.android.turnaround.data.remote.model.response.UserSetResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserSetService {

    @GET("v1/user/me/onboarding/check")
    suspend fun checkUserSet() :BaseResponse<UserSetResponse>

    @POST("v1/user/onboarding")
    suspend fun postUserSet(
        @Body body: PostUserSetRequest
    ):BaseResponse<BasicResponse>
}