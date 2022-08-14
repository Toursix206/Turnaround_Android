package org.android.turnaround.presentation.roomsetting

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomSettingStep2Binding
import org.android.turnaround.domain.entity.RoomSetting
import org.android.turnaround.presentation.base.BaseFragment

class RoomSettingStep2Fragment : BaseFragment<FragmentRoomSettingStep2Binding>(R.layout.fragment_room_setting_step2),
    View.OnClickListener{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        setVisibleAnimationListener()
        setRoomSettingAdapter()
    }

    private fun setVisibleAnimationListener() {
        binding.ivArrow1.setOnClickListener(this@RoomSettingStep2Fragment)
        binding.ivArrow2.setOnClickListener(this@RoomSettingStep2Fragment)
        binding.ivArrow3.setOnClickListener(this@RoomSettingStep2Fragment)
        binding.ivArrow4.setOnClickListener(this@RoomSettingStep2Fragment)
    }

    private fun setRoomSettingAdapter() {
        val arrBed = arrayListOf(RoomSetting("침대", false), RoomSetting("침구류", false))
        val arrWashing = arrayListOf(RoomSetting("세탁기", false), RoomSetting("빨래걸이", false))
        val arrOffice  = arrayListOf(RoomSetting("책상", false), RoomSetting("의자", false),
            RoomSetting("컴퓨터", false))
        val arrEx = arrayListOf(RoomSetting("주방", false), RoomSetting("화장실", false),
            RoomSetting("창문", false))
        binding.rv1.adapter = RoomSettingStep2Adapter(arrBed)
        binding.rv2.adapter = RoomSettingStep2Adapter(arrWashing)
        binding.rv3.adapter = RoomSettingStep2Adapter(arrOffice)
        binding.rv4.adapter = RoomSettingStep2Adapter(arrEx)
    }

    private fun checkNextButtonEnable() {
        val result1 = (binding.rv1.adapter as RoomSettingStep2Adapter).getCheckedItems()
        val result2 = (binding.rv2.adapter as RoomSettingStep2Adapter).getCheckedItems()
        val result3 = (binding.rv3.adapter as RoomSettingStep2Adapter).getCheckedItems()
        val result4 = (binding.rv4.adapter as RoomSettingStep2Adapter).getCheckedItems()
        binding.btnNext.isEnabled = result1 && result2 && result3 && result4
    }

    private fun startVisibilityAnimation(expandView: View, toggleImage: ImageButton, isVisible: Boolean) {
        expandView.visibility = if(isVisible) View.VISIBLE else View.GONE
        toggleImage.animate().setDuration(200).rotation(if(isVisible) 0f else 180f)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.ivArrow1.id -> {
                if (binding.layoutExpand1.visibility == View.GONE) {
                    startVisibilityAnimation(binding.layoutExpand1, binding.ivArrow1, true)
                    startVisibilityAnimation(binding.layoutExpand2, binding.ivArrow2, false)
                    startVisibilityAnimation(binding.layoutExpand3, binding.ivArrow3, false)
                    startVisibilityAnimation(binding.layoutExpand4, binding.ivArrow4, false)
                }
                else startVisibilityAnimation(binding.layoutExpand1, binding.ivArrow1, false)
            }
            binding.ivArrow2.id -> {
                if (binding.layoutExpand2.visibility == View.GONE) {
                    startVisibilityAnimation(binding.layoutExpand1, binding.ivArrow1, false)
                    startVisibilityAnimation(binding.layoutExpand2, binding.ivArrow2, true)
                    startVisibilityAnimation(binding.layoutExpand3, binding.ivArrow3, false)
                    startVisibilityAnimation(binding.layoutExpand4, binding.ivArrow4, false)
                }
                else startVisibilityAnimation(binding.layoutExpand2, binding.ivArrow2, false)
            }
            binding.ivArrow3.id -> {
                if (binding.layoutExpand3.visibility == View.GONE) {
                    startVisibilityAnimation(binding.layoutExpand1, binding.ivArrow1, false)
                    startVisibilityAnimation(binding.layoutExpand2, binding.ivArrow2, false)
                    startVisibilityAnimation(binding.layoutExpand3, binding.ivArrow3, true)
                    startVisibilityAnimation(binding.layoutExpand4, binding.ivArrow4, false)
                }
                else startVisibilityAnimation(binding.layoutExpand3, binding.ivArrow3, false)
            }
            binding.ivArrow4.id -> {
                if (binding.layoutExpand4.visibility == View.GONE) {
                    startVisibilityAnimation(binding.layoutExpand1, binding.ivArrow1, false)
                    startVisibilityAnimation(binding.layoutExpand2, binding.ivArrow2, false)
                    startVisibilityAnimation(binding.layoutExpand3, binding.ivArrow3, false)
                    startVisibilityAnimation(binding.layoutExpand4, binding.ivArrow4, true)
                }
                else startVisibilityAnimation(binding.layoutExpand4, binding.ivArrow4, false)
            }
        }
        checkNextButtonEnable()
    }

}