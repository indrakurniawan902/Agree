package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemActivitySopDialogBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.SubPhaseActivity
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class SubPhaseActivityAdapter(
    private val onItemClicked: (subPhaseActivity: SubPhaseActivity) -> Unit
) : DevRecyclerViewAdapter<SubPhaseActivity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<SubPhaseActivity> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemActivitySopDialogBinding.inflate(getLayoutInflater(parent), parent, false)
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

    inner class ViewHolder(private val bindLayout: ItemActivitySopDialogBinding) :
        DevItemViewHolder<SubPhaseActivity>(bindLayout) {
        override fun bind(data: SubPhaseActivity?) {
            with(bindLayout) {
                data?.let { subPhaseActivity ->
                    tvActivityName.text = subPhaseActivity.phaseName
                    tvActivityDesc.text = subPhaseActivity.description
                    root.setOnClickListener {
                        onItemClicked(subPhaseActivity)
                    }
                }
            }
        }
    }
}
