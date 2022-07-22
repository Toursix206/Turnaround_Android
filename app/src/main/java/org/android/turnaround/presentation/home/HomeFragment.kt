package org.android.turnaround.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import org.android.turnaround.R
import org.android.turnaround.data.model.Banner
import org.android.turnaround.data.model.Kit
import org.android.turnaround.data.model.Work
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.presentation.base.BaseFragment

class HomeFragment :  BaseFragment<FragmentHomeBinding>(R.layout.fragment_home)  {
    private lateinit var callback: OnBackPressedCallback

    private final val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWorkAdapter()
        setKitAdapter()
        setBannerAdapter()
    }

    private fun setWorkAdapter() {
        val workArr = arrayListOf(
            Work("곰팡이 청소1", "20:15:33"),
            Work("곰팡이 청소2", "20:15:33"),
            Work("곰팡이 청소3", "20:15:33"),
            Work("곰팡이 청소4", "20:15:33")
        )
        binding.rvWork.adapter = WorkAdapter().apply {
            submitList(workArr)
        }
    }

    private fun setKitAdapter() {
        val kitUrl = "https://user-images.githubusercontent.com/61674991/180229556-0cb49e88-22ea-40ca-8b4e-bfb1e07c3894.png"
        val kitArr = arrayListOf(
            Kit("화장실", "팡이 팡이\n곰팡이", kitUrl),
            Kit("화장실", "팡이 팡이\n곰팡이", kitUrl),
            Kit("화장실", "팡이 팡이\n곰팡이", kitUrl),
            Kit("화장실", "팡이 팡이\n곰팡이", kitUrl)
        )
        binding.vpKit.adapter = KitAdapter().apply {
            submitList(kitArr)
        }
    }

    private fun setBannerAdapter() {
        val url = "https://user-images.githubusercontent.com/61674991/180220893-4fde51de-e8e9-4ea9-9f03-4bbe66e7281a.png"
        val bannerArr = arrayListOf(
            Banner(url, "진행중 |  2022.05.01~2023.05.01"),
            Banner(url, "진행중 |  2022.05.01~2023.05.01"),
            Banner(url, "진행중 |  2022.05.01~2023.05.01"),
            Banner(url, "진행중 |  2022.05.01~2023.05.01")
        )
        binding.vpBanner.adapter = BannerAdapter().apply {
            submitList(bannerArr)
        }
        TabLayoutMediator(binding.tabBanner, binding.vpBanner) { _, _ -> }.attach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 뒤로가기 한 번 누르면 메인 화면으로 이동, 2초 내에 한 번 더 누르면 종료
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(parentFragmentManager.backStackEntryCount == 0) {
                    val tempTime = System.currentTimeMillis()
                    val intervalTime = tempTime - backPressedTime
                    if (intervalTime in 0..FINISH_INTERVAL_TIME) requireActivity().finish()
                    else {
                        backPressedTime = tempTime
                        binding.scrollView.smoothScrollTo(0, binding.toolbar.top)
                        return
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@HomeFragment, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}