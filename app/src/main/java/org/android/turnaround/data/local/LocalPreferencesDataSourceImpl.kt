package org.android.turnaround.data.local

import android.content.SharedPreferences
import com.kakao.sdk.auth.Constants.ACCESS_TOKEN
import javax.inject.Inject

class LocalPreferencesDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
): LocalPreferencesDataSource {


    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    override fun getAccessToken(): String =
        localPreferences.getString(ACCESS_TOKEN, "") ?: ""


    override fun getRefreshToken(): String =
        localPreferences.getString(REFRESH_TOKEN, "") ?: ""

    override fun setAccessToken(jwt: String) {
        localPreferences.edit().putString(ACCESS_TOKEN, jwt).apply()
    }

    override fun setRefreshToken(refresh: String) {
        localPreferences.edit().putString(REFRESH_TOKEN, refresh).apply()
    }

    override fun deleteAccessToken() {
        localPreferences.edit()
            .remove(ACCESS_TOKEN)
            .apply()
    }


}