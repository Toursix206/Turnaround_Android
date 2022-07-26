package org.android.turnaround.data.remote.model.request

data class PostLoginRequest(
    val socialType: String,
    val token: String
)