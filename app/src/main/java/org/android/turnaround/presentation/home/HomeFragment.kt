package org.android.turnaround.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
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
    private lateinit var kitAdapter: KitAdapter
    private var backPressedTime: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWorkAdapter()
        setBannerAdapter()
        setKitAdapter()

        setBannerAnimationListener()

        setCategoryBtnFilter()
        setMainTabFilter()
        setSubTabFilter()
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

    private fun setKitAdapter() {
        val kitUrl1 =
            "https://user-images.githubusercontent.com/61674991/180229556-0cb49e88-22ea-40ca-8b4e-bfb1e07c3894.png"
        val kitUrl2 =
            "https://user-images.githubusercontent.com/61674991/180587738-5ffc8c79-203b-4c8a-9e70-008058802b53.png"
        val kitArr = arrayListOf(
            Kit("무료활동", "#000000", "화장실", "팡이 팡이\n곰팡이",
                "무료, 0원 6/1, 1000", kitUrl1, 0, "2022-06-01", 1000),
            Kit("무료활동", "#000000", "세탁기", "향기로\n밀린 빨래\n재탄생",
                "무료, 0원 7/2, 3000", kitUrl2, 0, "2022-07-02", 3000),
            Kit("무료활동", "#9747FF", "세탁기", "향기로\n밀린 빨래\n재탄생",
                "유료, 1000원 6/10, 2000", kitUrl2, 1000, "2022-06-10", 2000),
            Kit("무료활동", "#000000", "화장실", "팡이 팡이\n곰팡이",
                "무료, 0원 7/24, 10", kitUrl1, 0, "2022-07-24", 10),
        )
        kitAdapter = KitAdapter(kitArr)
        binding.vpKit.adapter = kitAdapter
        binding.vpKit.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val myFragment = childFragmentManager.findFragmentByTag("f$position")
                myFragment?.view?.let { updatePagerHeightForChild(it, binding.vpKit) }
            }
        })
        binding.vpKit.setPageTransformer(DepthPageTransformer())
    }

    private fun setMainTabFilter() {
        val blue = "#1371ff"
        val purple = "#9747FF"
        val gray = "#dddddd"
        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        kitAdapter.filter.filter(FILTER_FREE)
                        changeMainTabTextColor(blue, gray)
                    }
                    1 -> {
                        kitAdapter.filter.filter(FILTER_RECOMMEND)
                        changeMainTabTextColor(gray, purple)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    fun changeMainTabTextColor(color1: String, color2: String) {
        binding.tab1.setTextColor(Color.parseColor(color1))
        binding.tab11.setTextColor(Color.parseColor(color1))
        binding.tab2.setTextColor(Color.parseColor(color2))
        binding.tab21.setTextColor(Color.parseColor(color2))
    }

    private fun setCategoryBtnFilter() {
        val arrCategory = resources.getStringArray(R.array.home_category_arr)
        var clickCount = 0
        val maxCount = arrCategory.size - 1
        binding.btnCategory.setOnClickListener {
            if (clickCount == maxCount) clickCount = 0
            val category = arrCategory[clickCount++]
            binding.tvCategoryKor.text = category
            kitAdapter.filter.filter(if(category == "전체") "" else category)
        }
    }

    private fun setBannerAnimationListener() {
        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            val view = binding.scrollView.getChildAt(binding.scrollView.childCount - 1)
            val topDetector = binding.scrollView.scrollY
            val bottomDetector: Int = view.bottom - (binding.scrollView.height + binding.scrollView.scrollY)
            // 바닥에 닿으면: 광고 배너 보이기
            if (bottomDetector == 0) setBannerVisibleGoneAnimation(binding.movableLayout, true)
            // 위에 닿으면: 광보 배너 숨기기
            else if (topDetector <= 0) setBannerVisibleGoneAnimation(binding.movableLayout, false)
        }
    }
    private fun setBannerVisibleGoneAnimation(view: View, visible: Boolean) {
        view.animate()
            .alpha(if (visible) 1.0f else 0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.visibility = if (visible) View.VISIBLE else View.GONE
                }
            })
    }

    private fun setSubTabFilter() {
        binding.tabSub.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> kitAdapter.filter.filter(FILTER_NEW)
                    1 -> kitAdapter.filter.filter(FILTER_POPULAR)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun updatePagerHeightForChild(view: View, pager: ViewPager2) {
        view.post {
            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(wMeasureSpec, hMeasureSpec)
            if (pager.layoutParams.height != view.measuredHeight) {
                pager.layoutParams = (pager.layoutParams)
                    .also { lp -> lp.height = view.measuredHeight }
            }
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