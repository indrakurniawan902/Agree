package com.agree.ecosystem.utilities.presentation.menu.zone.province

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.agree.ecosystem.utilities.databinding.ItemLocationBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class ProvinceAdapter(
    private val onItemClick: (province: Province) -> Unit
) : DevRecyclerViewAdapter<Province>(), Filterable {

    private lateinit var provinces: List<Province?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Province> =
        ViewHolder(ItemLocationBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemLocationBinding) :
        DevItemViewHolder<Province>(bindLayout) {
        override fun bind(data: Province?) {
            with(bindLayout) {
                data?.let { province ->
                    tvContent.text = province.name
                    root.setOnClickListener {
                        onItemClick(province)
                    }
                }
            }
        }
    }

    fun setProvinces(provinces: List<Province?>) {
        this.provinces = provinces
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Province?>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(provinces)
            } else {
                provinces.forEach {
                    it?.let { province ->
                        if (province.name.contains(constraint.toString(), true)) {
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
            addOrUpdateAll(filterResults?.values as MutableList<Province>)
        }
    }
}
