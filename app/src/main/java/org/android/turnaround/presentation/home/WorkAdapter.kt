package org.android.turnaround.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.data.model.Work
import org.android.turnaround.databinding.ItemWorkHomeBinding

// Filterable
class WorkAdapter: ListAdapter<Work, WorkAdapter.WorkViewHolder>(WorkDiffCallback()) {
    private lateinit var binding: ItemWorkHomeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        binding = ItemWorkHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WorkViewHolder(private val binding: ItemWorkHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(work: Work) {
            binding.work = work
            binding.executePendingBindings()
        }
    }
}

class WorkDiffCallback: DiffUtil.ItemCallback<Work>() {
    override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
        return oldItem.label == newItem.label
    }
    override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
        return oldItem == newItem
    }
}