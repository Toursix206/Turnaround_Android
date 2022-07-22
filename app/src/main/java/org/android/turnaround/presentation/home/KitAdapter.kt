package org.android.turnaround.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.data.model.Kit
import org.android.turnaround.databinding.ItemKitBinding

// Filterable
class KitAdapter: ListAdapter<Kit, KitAdapter.KitViewHolder>(KitDiffCallback()) {
    private lateinit var binding: ItemKitBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitViewHolder {
        binding = ItemKitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class KitViewHolder(private val binding: ItemKitBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kit: Kit) {
            binding.kit = kit
            binding.executePendingBindings()
        }
    }
}

class KitDiffCallback: DiffUtil.ItemCallback<Kit>() {
    override fun areItemsTheSame(oldItem: Kit, newItem: Kit): Boolean {
        return oldItem.label == newItem.label
    }
    override fun areContentsTheSame(oldItem: Kit, newItem: Kit): Boolean {
        return oldItem == newItem
    }
}