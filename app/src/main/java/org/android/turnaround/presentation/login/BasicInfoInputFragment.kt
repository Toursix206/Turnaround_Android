package org.android.turnaround.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentBasicInfoInputBinding
import org.android.turnaround.presentation.base.BaseFragment

@AndroidEntryPoint
class BasicInfoInputFragment :
    BaseFragment<FragmentBasicInfoInputBinding>(R.layout.fragment_basic_info_input) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = loginViewModel
        setListeners()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_basicInfoInputFragment_to_addressInfoInputFragment)
        }
    }


}