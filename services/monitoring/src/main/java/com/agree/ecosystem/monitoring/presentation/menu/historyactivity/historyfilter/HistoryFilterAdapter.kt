package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemActivityHistorySopBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopGroupByDate
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class HistoryFilterAdapter(
    private val onItemClicked: (activity: ActivitySop) -> Unit,
) : DevRecyclerViewAdapter<ActivitySopGroupByDate>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<ActivitySopGroupByDate> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemActivityHistorySopBinding.inflate(getLayoutInflater(parent), parent, false)
            )
            else -> DevEndlessItemViewHolder(
                ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                getLoadMoreListener(),
                isLoading,
                loadMoreSkip,
                loadMoreLimit
            )
        }

    override fun getItemViewType(position: Int): Int =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemActivityHistorySopBinding) :
        DevItemViewHolder<ActivitySopGroupByDate>(bindLayout) {
        override fun bind(data: ActivitySopGroupByDate?) {
            with(bindLayout) {
                data?.let { parentActivity ->
                    tvDate.text = parentActivity.getSimpleDayDate()
                    val subActivitySopAdapter = SubHistoryFilterAdapter(onItemClicked = {
                        onItemClicked(it)
                    })
                    subActivitySopAdapter.addOrUpdateAll(parentActivity.activitySop)
                    rvItemActivitySop.adapter = subActivitySopAdapter
                }
            }
        }
    }
}
