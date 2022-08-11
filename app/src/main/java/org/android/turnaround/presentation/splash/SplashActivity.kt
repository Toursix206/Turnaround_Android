package org.android.turnaround.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivitySplashBinding
import org.android.turnaround.presentation.base.BaseActivity
import org.android.turnaround.presentation.login.LoginViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}