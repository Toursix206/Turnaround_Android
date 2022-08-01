package org.android.turnaround.data.local

interface LocalPreferencesDataSource {

    fun getAccessToken():String

    fun getRefreshToken() :String

    fun setAccessToken(jwt:String)

    fun setRefreshToken(refresh:String)

    fun deleteAccessToken()

}