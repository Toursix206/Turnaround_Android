package org.android.turnaround.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.turnaround.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    var address = MutableLiveData<String>()
    var addressDetail = MutableLiveData<String>("")
    var addressPw = MutableLiveData("")

    var kakaoToken = MutableLiveData<String>()

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(LoginFragment.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(LoginFragment.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            postLogin(token.accessToken)
        }
    }

    fun postLogin(token:String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.postLogin(token)
            }.onSuccess {
                Timber.d("asdf${it.data}")
            }
        }
    }
}