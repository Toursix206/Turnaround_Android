package org.android.turnaround.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.domain.entity.Kit
import org.android.turnaround.databinding.ItemKitBinding

/*
1. 무료/추천(무료/유료)<tag: const val
2. 최신순/인기순<tag: const val
3. 카테고리별(전체- 책상 - 세탁기 -화장실 - 주방 - 침대 - 창문)
*/

// Filterable
class KitAdapter(val item: List<Kit>): RecyclerView.Adapter<KitAdapter.KitViewHolder>(), Filterable {
    private lateinit var binding: ItemKitBinding

    private var unFilteredList = item
    private var filteredList = item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitViewHolder {
        binding = ItemKitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KitViewHolder, position: Int) {
        val item = filteredList[position]
        holder.bind(item)
    }

    inner class KitViewHolder(private val binding: ItemKitBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(kit: Kit) {
            binding.kit = kit
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val tag = constraint.toString().split(" ")
                val mainFilter = tag[0]
                val subFilter = tag[1]
                val categoryFilter = if (tag[2] == FILTER_CATEGORY_ALL) "" else tag[2]

                val filteringList = arrayListOf<Kit>()
                // 메인 필터(무료/추천) + 카테고리
                if (mainFilter == FILTER_MAIN_FREE) {
                    for (item in unFilteredList)
                        if (item.price == 0 && item.category.contains(categoryFilter))
                            filteringList.add(item)
                }
                else {
                    for (item in unFilteredList)
                        if (item.price != 0 && item.category.contains(categoryFilter))
                            filteringList.add(item)
                }
                // 서브 필터(최신/인기)
                if (subFilter == FILTER_SUB_NEW) filteringList.sortByDescending { it.updated }
                else filteringList.sortByDescending { it.like }

                filteredList = filteringList
                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Kit>
                notifyDataSetChanged()
            }
        }
    }
}