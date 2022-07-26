package org.android.turnaround.data.remote.model.response

import org.android.turnaround.data.remote.model.Token

data class PostLoginResponse(
    val data: Data,
    val message: String,
    val resultCode: Int
) {
    data class Data(
        val token: Token,
        val userId: Int
    )
}