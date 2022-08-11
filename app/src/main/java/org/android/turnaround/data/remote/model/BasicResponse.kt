package org.android.turnaround.data.remote.model

data class BasicResponse(
    val data:String?,
    val success:Boolean,
    val message:String,
    val status:Int
)
