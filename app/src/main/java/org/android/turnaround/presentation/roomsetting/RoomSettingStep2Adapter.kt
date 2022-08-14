package org.android.turnaround.presentation.roomsetting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.R
import org.android.turnaround.databinding.ItemRoomSettingBinding
import org.android.turnaround.domain.entity.RoomSetting

class RoomSettingStep2Adapter(var arrRoomSetting: ArrayList<RoomSetting>): RecyclerView.Adapter<RoomSettingStep2Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomSettingStep2Adapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemRoomSettingBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int =
        arrRoomSetting.size

    override fun onBindViewHolder(holder: RoomSettingStep2Adapter.ViewHolder, position: Int) =
        holder.setItem(arrRoomSetting[position], position)

    fun getCheckedItems(): Boolean {
        for (item in arrRoomSetting) if (item.isChecked) return true
        return false
    }

    inner class ViewHolder(private val binding: ItemRoomSettingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: RoomSetting, position: Int) {
            binding.item = item
            binding.executePendingBindings()

            binding.cb.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) setCheckedView(binding.layoutBg, binding.tvTitle, binding.cb, position)
                else setUnCheckedView(binding.layoutBg, binding.tvTitle, binding.cb, position)
            }
            binding.layoutBg.setOnClickListener { binding.cb.isChecked = !binding.cb.isChecked }
        }

        private fun setCheckedView(view: View, tv: TextView, cb: CheckBox, position: Int) {
            view.setBackgroundResource(R.drawable.bg_black_r5)
            tv.setTextAppearance(R.style.TextMedium15_Bold_White)
            cb.isChecked = true
            arrRoomSetting[position].isChecked = true
        }
        private fun setUnCheckedView(view: View, tv: TextView, cb: CheckBox, position: Int) {
            view.setBackgroundResource(R.drawable.bg_gray_r5)
            tv.setTextAppearance(R.style.TextMedium15_Bold)
            cb.isChecked = false
            arrRoomSetting[position].isChecked = false
        }
    }
}