package org.android.turnaround.data.remote.model.request

data class PostUserSetRequest(
    val address: String,
    val cleanAbility: String,
    val detailAddress: String,
    val gatePassword: String,
    val gender: String
)