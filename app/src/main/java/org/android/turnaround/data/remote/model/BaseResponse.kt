package org.android.turnaround.data.remote.model

data class BaseResponse<T>(
    val data:T,
    val status:Int,
    val message:String,
    val success:Boolean
)
