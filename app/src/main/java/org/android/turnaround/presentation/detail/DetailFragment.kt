package org.android.turnaround.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentDetailBinding
import org.android.turnaround.presentation.base.BaseFragment

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val tabTitleArray = arrayOf(
        "Tab1",
        "Tab2"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager2()
    }

    private fun initViewPager2() {
        binding.vpDetail.adapter = ScreenSlidePagerAdapter(requireActivity())
        binding.vpDetail.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpDetail.isUserInputEnabled = false
        // 뷰페이저와 탭레이아웃을 붙임
        TabLayoutMediator(binding.tlDetail, binding.vpDetail) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2 // 페이지 수 리턴

        override fun createFragment(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> WorkInfoFragment()
                else -> WorkReviewFragment()
            }
        }
    }

}