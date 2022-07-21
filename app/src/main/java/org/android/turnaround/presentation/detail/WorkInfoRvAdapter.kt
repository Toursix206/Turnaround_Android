package org.android.turnaround.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.R
import org.android.turnaround.databinding.ItemWorkBinding

class WorkInfoRvAdapter : RecyclerView.Adapter<WorkInfoRvAdapter.WorkInfoViewHolder>() {

    var workInfoImage = arrayListOf<Int>(
        R.color.black,
        R.color.black,
    )


    inner class WorkInfoViewHolder(private val binding: ItemWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Int, position: Int) {
            binding.ivWork.setImageResource(data)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WorkInfoViewHolder {
        val binding =
            ItemWorkBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return WorkInfoViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: WorkInfoViewHolder, position: Int) {
        viewHolder.onBind(workInfoImage[position], position)

    }

    override fun getItemCount() = workInfoImage.size
}