package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemIndividualMonitoringBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding

class IndividualMonitoringAdapter(
    onItemClicked: (individualMonitoringId: IndividualSubVessel?) -> Unit
) : DevRecyclerViewAdapter<IndividualSubVessel>(onItemClicked) {

    inner class IndividualViewHolder(private val bindLayout: ItemIndividualMonitoringBinding) :
        DevItemViewHolder<IndividualSubVessel>(bindLayout) {
        override fun bind(data: IndividualSubVessel?) {
            with(bindLayout) {
                data?.let {
                    tvIndividualMonitoringId.text = data.code.replace("\n", String())
                }
            }
        }
    }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<IndividualSubVessel> {
        return when (viewType) {
            CONTENT -> IndividualViewHolder(inflateBinding(parent))
            else -> DevEndlessItemViewHolder(
                ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                getLoadMoreListener(),
                isLoading,
                loadMoreSkip,
                loadMoreLimit
            )
        }
    }
}
