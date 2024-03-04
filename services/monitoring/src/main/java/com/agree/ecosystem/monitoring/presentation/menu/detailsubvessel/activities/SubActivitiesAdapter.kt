package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.SubItemActivitySopBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.util.getDrawableResource

class SubActivitiesAdapter(
    private val onItemClicked: (activitySop: ActivitySop) -> Unit
) : DevRecyclerViewAdapter<ActivitySop>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<ActivitySop> =
        when (viewType) {
            CONTENT -> ViewHolder(
                SubItemActivitySopBinding.inflate(getLayoutInflater(parent), parent, false)
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

    inner class ViewHolder(private val bindLayout: SubItemActivitySopBinding) :
        DevItemViewHolder<ActivitySop>(bindLayout) {
        override fun bind(data: ActivitySop?) {
            with(bindLayout) {
                data?.let { activitySop ->
                    tvPhaseName.text = data.phaseName
                    tvActivityName.text = data.getDetailActivity()
                    tvDescription.text = data.description
                    if (data.isCompleted) {
                        imgInformation.setImageDrawable(getDrawableResource(R.drawable.ic_check_circle))
                    } else {
                        imgInformation.gone()
                    }

                    root.setOnClickListener {
                        onItemClicked(activitySop)
                    }
                }
            }
        }
    }
}
