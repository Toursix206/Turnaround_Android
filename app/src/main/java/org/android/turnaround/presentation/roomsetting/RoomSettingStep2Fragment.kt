package org.android.turnaround.presentation.roomsetting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomSettingStep2Binding
import org.android.turnaround.presentation.base.BaseFragment

class RoomSettingStep2Fragment : BaseFragment<FragmentRoomSettingStep2Binding>(R.layout.fragment_room_setting_step2){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

}