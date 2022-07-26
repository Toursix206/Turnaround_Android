package org.android.turnaround.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentBasicInfoInputBinding
import org.android.turnaround.presentation.base.BaseFragment

class BasicInfoInputFragment : BaseFragment<FragmentBasicInfoInputBinding>(R.layout.fragment_basic_info_input) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.btnNext.setOnClickListener {  }
    }

}