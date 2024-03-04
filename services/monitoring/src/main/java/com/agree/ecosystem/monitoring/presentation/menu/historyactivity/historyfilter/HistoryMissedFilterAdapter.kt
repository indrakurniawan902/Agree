package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemActivityHistorySopBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesMissed
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesSopMissed
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class HistoryMissedFilterAdapter(
    private val onItemClicked: (activity: ActivitiesMissed) -> Unit,
) : DevRecyclerViewAdapter<ActivitiesSopMissed>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<ActivitiesSopMissed> =
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
        DevItemViewHolder<ActivitiesSopMissed>(bindLayout) {
        override fun bind(data: ActivitiesSopMissed?) {
            with(bindLayout) {
                data?.let { parentActivity ->
                    tvDate.text = parentActivity.getSimpleMissedDayDate()
                    val subActivityMissedSopAdapter = SubHistoryMissedFilterAdapter(onItemClicked = {
                        onItemClicked(it)
                    })
                    subActivityMissedSopAdapter.addOrUpdateAll(parentActivity.activities)
                    rvItemActivitySop.adapter = subActivityMissedSopAdapter
                }
            }
        }
    }
}
