package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemYearBinding
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class SelectYearAdapter(
    private val onItemClicked: (year: Int) -> Unit
) : DevRecyclerViewAdapter<Int>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Int> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemYearBinding.inflate(getLayoutInflater(parent), parent, false)
            )
            else -> DevEndlessItemViewHolder(
                ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                getLoadMoreListener(),
                isLoading,
                loadMoreSkip,
                loadMoreLimit
            )
        }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemYearBinding) :
        DevItemViewHolder<Int>(bindLayout) {

        override fun bind(data: Int?) {
            with(bindLayout) {
                data?.let { value ->
                    tvYear.text = value.toString()
                    root.setOnClickListener {
                        onItemClicked(value)
                    }
                }
            }
        }
    }
}
