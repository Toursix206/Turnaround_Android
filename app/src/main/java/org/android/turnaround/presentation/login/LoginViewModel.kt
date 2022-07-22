package org.android.turnaround.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken

class LoginViewModel: ViewModel() {

    // 카카오계정으로 로그인 공통 callback 구성
// 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(LoginFragment.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(LoginFragment.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }
}