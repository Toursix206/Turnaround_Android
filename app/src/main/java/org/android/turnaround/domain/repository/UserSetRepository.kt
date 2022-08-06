package org.android.turnaround.domain.repository

import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.BasicResponse
import org.android.turnaround.data.remote.model.response.UserSetResponse

interface UserSetRepository {

    suspend fun isUserSet() :Result<BaseResponse<UserSetResponse>>

    suspend fun setUserInfo(address:String, detailAddress:String, gender:String, cleanLevel:String, doorPw:String) :Result<BaseResponse<BasicResponse>>
}