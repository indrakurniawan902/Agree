package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemActivitySopDialogBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class PhaseActivityAdapter(
    private val onItemClicked: (phaseActivity: PhaseActivity) -> Unit
) : DevRecyclerViewAdapter<PhaseActivity>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<PhaseActivity> =
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
        DevItemViewHolder<PhaseActivity>(bindLayout) {

        override fun bind(data: PhaseActivity?) {
            with(bindLayout) {
                data?.let { phaseActivity ->
                    tvActivityName.text = phaseActivity.phaseName
                    tvActivityDesc.text = phaseActivity.description
                    root.setOnClickListener {
                        onItemClicked(phaseActivity)
                    }
                }
            }
        }
    }
}
