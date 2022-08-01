package org.android.turnaround.data.repository

import kotlinx.coroutines.withContext
import org.android.turnaround.data.remote.datasource.LoginDataSource
import org.android.turnaround.data.remote.model.BaseResponse
import org.android.turnaround.data.remote.model.BasicResponse
import org.android.turnaround.data.remote.model.request.PostUserSetRequest
import org.android.turnaround.data.remote.model.response.UserSetResponse
import org.android.turnaround.domain.repository.UserSetRepository
import javax.inject.Inject

class UserSetRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
):UserSetRepository {
    override suspend fun isUserSet(): Result<BaseResponse<UserSetResponse>> =
        Result.success(loginDataSource.isChecked())

    override suspend fun setUserInfo(address:String, detailAddress:String, gender:String, cleanLevel:String, doorPw:String): Result<BaseResponse<BasicResponse>> =
        Result.success(loginDataSource.setUserInfo(PostUserSetRequest(address,cleanLevel,detailAddress,doorPw,gender)))
}