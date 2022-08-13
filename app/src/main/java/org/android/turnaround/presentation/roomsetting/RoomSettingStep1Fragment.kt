package org.android.turnaround.presentation.roomsetting

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomSettingStep1Binding
import org.android.turnaround.presentation.base.BaseFragment

class RoomSettingStep1Fragment : BaseFragment<FragmentRoomSettingStep1Binding>(R.layout.fragment_room_setting_step1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        setCheckListener()
    }

    private fun setClickListener() {
        binding.viewA.setOnClickListener { binding.cbA.isChecked = !binding.cbA.isChecked }
        binding.viewB.setOnClickListener { binding.cbB.isChecked = !binding.cbB.isChecked }
        binding.viewC.setOnClickListener { binding.cbC.isChecked = !binding.cbC.isChecked }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_roomSettingStep1Fragment_to_roomSettingStep2Fragment)
        }
    }

    private fun setCheckListener() {
        binding.cbA.setOnCheckedChangeListener { _, isChecked ->
            checkBoxIsChecked(isChecked, isCheckedB = false, isCheckedC = false)
            checkNextButtonEnabled(isChecked, isCheckedB = false, isCheckedC = false)
        }
        binding.cbB.setOnCheckedChangeListener { _, isChecked ->
            checkBoxIsChecked(false, isChecked, false)
            checkNextButtonEnabled(false, isChecked, false)
        }
        binding.cbC.setOnCheckedChangeListener { _, isChecked ->
            checkBoxIsChecked(false, isCheckedB = false, isCheckedC = isChecked)
            checkNextButtonEnabled(false, isCheckedB = false, isCheckedC = isChecked)
        }
    }

    private fun checkBoxIsChecked(isCheckedA: Boolean, isCheckedB: Boolean, isCheckedC: Boolean) {
        if (isCheckedA) setCheckedView(binding.viewA, binding.tvA, binding.cbA)
        else setUnCheckedView(binding.viewA, binding.tvA, binding.cbA)

        if (isCheckedB) setCheckedView(binding.viewB, binding.tvB, binding.cbB)
        else setUnCheckedView(binding.viewB, binding.tvB, binding.cbB)

        if (isCheckedC) setCheckedView(binding.viewC, binding.tvC, binding.cbC)
        else setUnCheckedView(binding.viewC, binding.tvC, binding.cbC)
    }

    private fun setCheckedView(view: View, tv: TextView, cb: CheckBox) {
        view.setBackgroundResource(R.drawable.bg_black_r5)
        tv.setTextAppearance(R.style.TextMedium15_Bold_White)
        cb.isChecked = true
        binding.btnNext.isEnabled = true
    }
    private fun setUnCheckedView(view: View, tv: TextView, cb: CheckBox) {
        view.setBackgroundResource(R.drawable.bg_gray_r5)
        tv.setTextAppearance(R.style.TextMedium15_Bold)
        cb.isChecked = false
    }

    private fun checkNextButtonEnabled(isCheckedA: Boolean, isCheckedB: Boolean, isCheckedC: Boolean) {
        binding.btnNext.isEnabled = isCheckedA || isCheckedB || isCheckedC
    }

}