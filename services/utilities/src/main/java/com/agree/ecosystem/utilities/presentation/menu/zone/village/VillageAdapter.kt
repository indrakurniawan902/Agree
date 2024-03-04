package com.agree.ecosystem.utilities.presentation.menu.zone.village

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.agree.ecosystem.utilities.databinding.ItemLocationBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class VillageAdapter(
    private val onItemClick: (village: Village) -> Unit
) : DevRecyclerViewAdapter<Village>(), Filterable {

    private lateinit var villages: List<Village?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Village> =
        ViewHolder(ItemLocationBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemLocationBinding) :
        DevItemViewHolder<Village>(bindLayout) {
        override fun bind(data: Village?) {
            with(bindLayout) {
                data?.let { village ->
                    tvContent.text = village.name
                    root.setOnClickListener {
                        onItemClick(village)
                    }
                }
            }
        }
    }

    fun setVillages(villages: List<Village?>) {
        this.villages = villages
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Village?>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(villages)
            } else {
                villages.forEach {
                    it?.let { village ->
                        if (village.name.contains(constraint.toString(), true)) {
                            filteredList.add(it)
                        }
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            clear()
            addOrUpdateAll(filterResults?.values as MutableList<Village>)
        }
    }
}
