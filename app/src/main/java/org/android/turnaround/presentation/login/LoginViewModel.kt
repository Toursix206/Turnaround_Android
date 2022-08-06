package org.android.turnaround.presentation.login

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.*
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.turnaround.R
import org.android.turnaround.domain.repository.AuthRepository
import org.android.turnaround.domain.repository.UserSetRepository
import org.android.turnaround.presentation.util.Event
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userSetRepository: UserSetRepository
) : ViewModel() {

    var kakaoTokenValid = MutableLiveData(false)
    var accessTokenValid = MutableLiveData(authRepository.getToken().isNotEmpty())

    var loginCheck = MediatorLiveData<Boolean>().apply {
        addSource(kakaoTokenValid) { value = isTokenValidCheck() }
        addSource(accessTokenValid) { value = isTokenValidCheck() }
    }

    private fun isTokenValidCheck() =
        kakaoTokenValid.value ?: false && accessTokenValid.value ?: false

    var userGender = MutableLiveData<String>()
    var userCleanLevel = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var addressDetail = MutableLiveData("")
    var addressPw = MutableLiveData("")


    var isSetBasicInfo = MediatorLiveData<Boolean>().apply {
        addSource(userGender) { value = it.isNotBlank() && userCleanLevel.value != null }
        addSource(userCleanLevel) { value = it.isNotBlank() && userGender.value != null }
    }

    var isSetAddressInfo = MediatorLiveData<Boolean>().apply {
        addSource(address) { value = it.isNotBlank() && addressDetail.value != "" }
        addSource(addressDetail) { value = it.isNotBlank() && address.value != null }
    }

    private val _successSetInfo = MutableLiveData<Event<Boolean>>()
    val successSetInfo: LiveData<Event<Boolean>> = _successSetInfo


    fun clickGender(view: View) {
        userGender.value = when ((view as TextView).text.toString()) {
            "남" -> "MAN"
            "여" -> "WOMAN"
            else -> ""
        }
    }

    fun clickCleanLevel(view: View) {
        userCleanLevel.value = when (view.id) {
            R.id.tv_clean_upper -> "GOOD"
            R.id.tv_clean_middle -> "NORMAL"
            R.id.tv_clean_lower -> "BAD"
            else -> ""
        }
    }

    fun postUserSet() {
        viewModelScope.launch {
            userSetRepository.setUserInfo(
                requireNotNull(address.value),
                requireNotNull(addressDetail.value),
                requireNotNull(userGender.value),
                requireNotNull(userCleanLevel.value),
                requireNotNull(addressPw.value),
            ).onSuccess {
                _successSetInfo.postValue(Event(true))

            }.onFailure {
                Timber.d("${it.message}")
            }
        }
    }

    var kakaoToken = MutableLiveData<String>()

    private var _isUserSet = MutableLiveData<Boolean>()
    val isUserSet: LiveData<Boolean> = _isUserSet

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(LoginFragment.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(LoginFragment.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            postLogin(token.accessToken)
        }
    }

    fun deleteAccessToken() {
        authRepository.deleteAccessToken()
    }

    private fun isUserSetChecked() {
        viewModelScope.launch {
            userSetRepository.isUserSet().onSuccess {
                _isUserSet.postValue(it.data.isChecked)
            }.onFailure {
                if ((it as? HttpException)?.code() == 401) {

                }
            }
        }
    }


    fun postLogin(token: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.postLogin(token)
            }.onSuccess {
                Timber.d("asdf${it.data}")
                authRepository.setToken(it.data.token)

                isUserSetChecked()

            }.onFailure {
                if ((it as? HttpException)?.code() == 401) {
                    postReLogin()
                } else {
                    Timber.e(it)
                }
                Timber.e(it)
            }
        }
    }

    private fun postReLogin() {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.postReLogin()
            }.onSuccess {
                // 토큰 재 설정 하고 다시 로그인 호출
            }.onFailure {
                // 401 경우에는 로그아웃
                if ((it as? HttpException)?.code() == 401) {

                }
            }
        }
    }


}