package org.android.turnaround.presentation.util

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

object KakaoService {

    fun isKakaoTokenValid(isValid: (Boolean) -> Any){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        //로그인 필요
                        isValid(false)
                    }
                    else {
                        //기타 에러
                    }
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    isValid(true)
                }
            }
        }
        else {
            //로그인 필요
            isValid(false)
        }

    }
}