package org.android.turnaround.data.remote.model.response

import org.android.turnaround.data.remote.model.Token

data class PostReLoginResponse(
    val data: Token,
    val message: String,
    val resultCode: Int
)