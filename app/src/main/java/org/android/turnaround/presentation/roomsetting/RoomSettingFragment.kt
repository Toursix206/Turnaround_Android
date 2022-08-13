package org.android.turnaround.presentation.roomsetting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomSettingBinding
import org.android.turnaround.presentation.base.BaseFragment

class RoomSettingFragment : BaseFragment<FragmentRoomSettingBinding>(R.layout.fragment_room_setting){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        binding.btnStartSet.setOnClickListener {
            findNavController().navigate(R.id.action_roomSettingFragment_to_roomSettingStep1Fragment)
        }
    }

}