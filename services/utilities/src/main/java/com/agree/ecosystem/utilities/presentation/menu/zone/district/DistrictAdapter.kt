package com.agree.ecosystem.utilities.presentation.menu.zone.district

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.agree.ecosystem.utilities.databinding.ItemLocationBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.District
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class DistrictAdapter(
    private val onItemClick: (district: District) -> Unit
) : DevRecyclerViewAdapter<District>(), Filterable {

    private lateinit var districts: List<District?>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<District> =
        ViewHolder(ItemLocationBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemLocationBinding) :
        DevItemViewHolder<District>(bindLayout) {
        override fun bind(data: District?) {
            with(bindLayout) {
                data?.let { district ->
                    tvContent.text = district.name
                    root.setOnClickListener {
                        onItemClick(district)
                    }
                }
            }
        }
    }

    fun setDistricts(districts: List<District?>) {
        this.districts = districts
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<District?>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(districts)
            } else {
                districts.forEach {
                    it?.let { district ->
                        if (district.name.contains(constraint.toString(), true)) {
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
            addOrUpdateAll(filterResults?.values as MutableList<District>)
        }
    }
}
