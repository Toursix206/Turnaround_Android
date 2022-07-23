package org.android.turnaround.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.android.turnaround.R
import org.android.turnaround.data.model.Banner
import org.android.turnaround.data.model.Kit
import org.android.turnaround.data.model.Work
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.presentation.base.BaseFragment


const val FINISH_INTERVAL_TIME: Long = 2000

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var callback: OnBackPressedCallback

    private var backPressedTime: Long = 0

    var toggle = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWorkAdapter()
        setKitAdapter()
        setBannerAdapter()
        setBtnCategory()
        setMainTabListener()
        setVisibleBannerListener()
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
        val kitUrl1 =
            "https://user-images.githubusercontent.com/61674991/180229556-0cb49e88-22ea-40ca-8b4e-bfb1e07c3894.png"
        val kitUrl2 =
            "https://user-images.githubusercontent.com/61674991/180587738-5ffc8c79-203b-4c8a-9e70-008058802b53.png"
        val kitArr = arrayListOf(
            Kit("무료활동", "#000000", "화장실", "팡이 팡이\n곰팡이",
                "화장실에 꽃이 폈어요. 샤워하면서 제거!", kitUrl1),
            Kit("무료활동", "#000000", "세탁기", "향기로\n밀린 빨래\n재탄생",
                "세탁조 청소부터 요즘 힙한 섬유유연코스!", kitUrl2),
            Kit("무료활동", "#000000", "세탁기", "향기로\n밀린 빨래\n재탄생",
                "세탁조 청소부터 요즘 힙한 섬유유연코스!", kitUrl2),
            Kit("무료활동", "#000000", "화장실", "팡이 팡이\n곰팡이",
                "화장실에 꽃이 폈어요. 샤워하면서 제거!", kitUrl1),
        )
        binding.vpKit.adapter = KitAdapter().apply {
            submitList(kitArr)
        }
    }

    private fun setBannerAdapter() {
        val url =
            "https://user-images.githubusercontent.com/61674991/180220893-4fde51de-e8e9-4ea9-9f03-4bbe66e7281a.png"
        val bannerArr = arrayListOf(
            Banner(url, "진행중 |  2022.05.01~2023.05.01"),
            Banner(url, "진행중 |  2022.05.01~2023.05.01"),
            Banner(url, "진행중 |  2022.05.01~2023.05.01"),
            Banner(url, "진행중 |  2022.05.01~2023.05.01")
        )
        binding.vpBanner.adapter = BannerAdapter().apply {
            submitList(bannerArr)
        }
        TabLayoutMediator(binding.tabBannerIndicator, binding.vpBanner) { _, _ -> }.attach()
    }

    private fun setMainTabListener() {
        val blue = "#1371ff"
        val purple = "#9747FF"
        val gray = "#dddddd"

        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selected = binding.tabMain.selectedTabPosition
                binding.tab1.setTextColor(Color.parseColor(if (selected == 0) blue else gray))
                binding.tab11.setTextColor(Color.parseColor(if (selected == 0) blue else gray))
                binding.tab2.setTextColor(Color.parseColor(if (selected == 0) gray else purple))
                binding.tab21.setTextColor(Color.parseColor(if (selected == 0) gray else purple))
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setBtnCategory() {
        val arrCategory = resources.getStringArray(R.array.home_category_arr)
        var clickCount = 0
        val maxCount = arrCategory.size - 1
        binding.btnCategory.setOnClickListener {
            if (clickCount == maxCount) clickCount = 0
            binding.tvCategoryKor.text = arrCategory[clickCount++]
        }
    }

    private fun setVisibleBannerListener() {
        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY < oldScrollY) //스크롤 다운: 광고배너 숨기기
                setVisibleGoneAnimation(binding.movableLayout, false)
            else //스크롤 업: 광고배너 보이기
                setVisibleGoneAnimation(binding.movableLayout, true)
        })
    }

    private fun setVisibleGoneAnimation(view: View, visible: Boolean) {
        if (toggle) {
            toggle = false
            view.animate()
                .alpha(if (visible) 1.0f else 0.0f)
                .setDuration(1500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        view.visibility = if (visible) View.VISIBLE else View.GONE
                        toggle = true
                    }
                })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 뒤로가기 한 번 누르면 메인 화면으로 이동, 2초 내에 한 번 더 누르면 종료
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount == 0) {
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