package com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.agree.ecosystem.utilities.databinding.ItemLocationBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class SubDistrictAdapter(
    private val onItemClick: (subDistrict: SubDistrict) -> Unit
) : DevRecyclerViewAdapter<SubDistrict>(), Filterable {

    private lateinit var subDistricts: List<SubDistrict?>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<SubDistrict> =
        ViewHolder(ItemLocationBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemLocationBinding) :
        DevItemViewHolder<SubDistrict>(bindLayout) {
        override fun bind(data: SubDistrict?) {
            with(bindLayout) {
                data?.let { subDistrict ->
                    tvContent.text = subDistrict.name
                    root.setOnClickListener {
                        onItemClick(subDistrict)
                    }
                }
            }
        }
    }

    fun setSubDistricts(subDistricts: List<SubDistrict?>) {
        this.subDistricts = subDistricts
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<SubDistrict?>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(subDistricts)
            } else {
                subDistricts.forEach {
                    it?.let { subDistrict ->
                        if (subDistrict.name.contains(constraint.toString(), true)) {
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
            addOrUpdateAll(filterResults?.values as MutableList<SubDistrict>)
        }
    }
}
