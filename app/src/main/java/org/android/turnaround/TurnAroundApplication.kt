package org.android.turnaround

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class TurnAroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}