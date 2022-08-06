package org.android.turnaround.presentation.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.domain.entity.Banner
import org.android.turnaround.domain.entity.Kit
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.presentation.base.BaseFragment

const val FILTER_MAIN_FREE = "무료"
const val FILTER_MAIN_RECOMMEND = "추천"
const val FILTER_SUB_NEW = "최신"
const val FILTER_SUB_LIKE = "인기"
const val FINISH_INTERVAL_TIME: Long = 2000

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var callback: OnBackPressedCallback
    private lateinit var kitAdapter: KitAdapter
    private var backPressedTime: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTodoAdapter()
        setBannerAdapter()
        setKitAdapter()

        setMoveHomePageAndDisplayPageListener()

        setKitFilter()
        setKitFloatingBtnListener()
    }

    private fun setTodoAdapter() {
        val todoArr = arrayListOf(
            Todo("화장실", "곰팡이 청소1", "D-3"),
            Todo("화장실", "곰팡이 청소2", "D-3"),
            Todo("화장실", "곰팡이 청소3", "D-3"),
            Todo("화장실", "곰팡이 청소4", "D-3"),
            Todo("책상", "책상 정리","D-2"),
            Todo("침대", "침대 정리", "20:15:33"),
            Todo("화장실", "곰팡이 청소5", "20:15:33")
        )
        binding.rvTodo.adapter = TodoAdapter().apply {
            submitList(todoArr)
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
        binding.vpBanner.adapter = BannerAdapter().apply { submitList(bannerArr) }
        binding.vpBanner.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val size = binding.vpBanner.adapter?.itemCount
                binding.tvBannerIndicator.text = "${position + 1} / $size"
            }
        })
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
        binding.vpKit.setPageTransformer(SlideUpTransformer())
    }

    private fun setMoveHomePageAndDisplayPageListener() {
        // 스크롤 불가
        binding.scrollView.setOnTouchListener { v, event -> true }
        // 키트 클릭 시 진열 페이지로 이동
        binding.viewKit.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, binding.layoutMainTab.top)
            binding.viewKit.visibility = View.GONE
            setVisibleGoneAnimation(binding.layoutMainTab, binding.layoutBanner)
            binding.btnFloatingKit.show()
        }
        // 메인 탭 레이아웃 클릭 시 홈 페이지로 이동
        binding.layoutMainTab.setOnClickListener {
            binding.viewKit.visibility = View.VISIBLE
            binding.scrollView.smoothScrollTo(0, binding.toolbar.top)
            setVisibleGoneAnimation(binding.layoutBanner, binding.layoutMainTab)
            binding.btnFloatingKit.hide()
        }
    }

    private fun setVisibleGoneAnimation(visibleView: View, invisibleView: View) {
        visibleView.animate().alpha(1.0f).duration = 300
        invisibleView.animate().alpha(0.0f).duration = 300
    }

    private fun setKitFilter() {
        // 필터 기본값
        var mainFilterValue = FILTER_MAIN_FREE
        var subFilterValue = FILTER_SUB_NEW
        var categoryFilterValue = "전체"

        // 메인 탭: 무료/추천
        val black = "#000000"
        val gray = "#e2e2e2"
        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mainFilterValue = if (tab?.position == 0) FILTER_MAIN_FREE else FILTER_MAIN_RECOMMEND
                kitAdapter.filter.filter("$mainFilterValue $subFilterValue $categoryFilterValue")
                binding.tab1.setTextColor(Color.parseColor(if (tab?.position == 0) black else gray))
                binding.tab2.setTextColor(Color.parseColor(if (tab?.position == 1) black else gray))
                binding.ivTab1.visibility = if (tab?.position == 0) View.VISIBLE else View.INVISIBLE
                binding.ivTab2.visibility = if (tab?.position == 1) View.VISIBLE else View.INVISIBLE
                binding.btnFloatingKit.callOnClick()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 서브 탭: 최신/인기
        binding.tabSub.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                subFilterValue = if (tab?.position == 0) FILTER_SUB_NEW else FILTER_SUB_LIKE
                kitAdapter.filter.filter("$mainFilterValue $subFilterValue $categoryFilterValue")
                binding.btnFloatingKit.callOnClick()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 카테고리
        val arrCategory = resources.getStringArray(R.array.home_category_arr)
        var clickCount = 0
        val maxCount = arrCategory.size - 1
        categoryFilterValue = arrCategory[clickCount++]
        binding.tvCategory.text = categoryFilterValue
        binding.tvCategory.setOnClickListener {
            if (clickCount == maxCount) clickCount = 0
            categoryFilterValue = arrCategory[clickCount++]
            binding.tvCategory.text = categoryFilterValue
            kitAdapter.filter.filter("$mainFilterValue $subFilterValue $categoryFilterValue")
            binding.btnFloatingKit.callOnClick()
        }
    }

    private fun setKitFloatingBtnListener() {
        binding.btnFloatingKit.setOnClickListener {
            binding.vpKit.setCurrentItem(0, true)
        }
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
                        binding.layoutMainTab.performClick()
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