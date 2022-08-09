package org.android.turnaround.presentation.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentMyPageBinding
import org.android.turnaround.presentation.base.BaseFragment

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.tvContact.setOnClickListener { showContactUs() }
        binding.tvPersonalInfo.setOnClickListener { showSetting() }
    }

    private fun showContactUs() {
        ContactUsFragment().show(childFragmentManager,ContactUsFragment().tag)
    }

    private fun showSetting() {
        SettingFragment().show(childFragmentManager,SettingFragment().tag)
    }

}