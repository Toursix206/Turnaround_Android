package org.android.turnaround.data.remote.model.response

import org.android.turnaround.data.remote.model.Token

data class PostLoginResponse(
    val token: Token,
    val userId: Int
)