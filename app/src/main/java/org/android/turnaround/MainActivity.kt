package org.android.turnaround

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.databinding.ActivityMainBinding
import org.android.turnaround.presentation.base.BaseActivity
import org.android.turnaround.presentation.login.LoginViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val loginViewModel: LoginViewModel by viewModels()


    private lateinit var navController: NavController
    lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        initIsLoginCheck()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        //navController.addOnDestinationChangedListener { _, destination, _ -> }
    }

    private fun initIsLoginCheck() {
        loginViewModel.loginCheck.observe(this) {
            goToNextPage(it)
        }
    }

    private fun goToNextPage(login: Boolean) {
        if (login) {
            navGraph.setStartDestination(R.id.homeFragment)
        } else {
            navGraph.setStartDestination(R.id.loginFragment)
        }
        navController.graph = navGraph
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            finish() // 액티비티 종료
        } else {
            super.onBackPressed()
        }
    }
}