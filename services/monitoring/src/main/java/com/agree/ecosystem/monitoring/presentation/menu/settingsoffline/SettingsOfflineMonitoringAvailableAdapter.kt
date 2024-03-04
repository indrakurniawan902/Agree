package com.agree.ecosystem.monitoring.presentation.menu.settingsoffline

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemSubAreaAvailableBinding
import com.agree.ecosystem.monitoring.databinding.ItemSubAreaLoadMoreBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible

class SettingsOfflineMonitoringAvailableAdapter(
    val onClickDownload: (SubVessel) -> Unit,
    val onClickLoadMore: () -> Unit
) : DevRecyclerViewAdapter<Pair<SubVessel, Boolean>>() {

    override fun onBindViewHolder(
        holder: DevItemViewHolder<Pair<SubVessel, Boolean>>,
        position: Int
    ) {
        when (holder.itemViewType) {
            LOAD_MORE -> {
                val binding = (holder as FooterViewHolder).bindLayout
                FooterViewHolder(binding).bind()
            }

            CONTENT -> super.onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<Pair<SubVessel, Boolean>> =
        when (viewType) {
            LOAD_MORE -> FooterViewHolder(
                ItemSubAreaLoadMoreBinding.inflate(
                    getLayoutInflater(parent),
                    parent,
                    false
                )
            )

            else -> ViewHolder(
                ItemSubAreaAvailableBinding.inflate(
                    getLayoutInflater(parent),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int {
        var count = super.getItemCount()
        if (withFooter) count++
        return count
    }

    override fun getItemViewType(position: Int) =
        if (withFooter && position == itemCount - 1) LOAD_MORE
        else CONTENT

    inner class ViewHolder(val bindLayout: ItemSubAreaAvailableBinding) :
        DevItemViewHolder<Pair<SubVessel, Boolean>>(bindLayout) {
        override fun bind(data: Pair<SubVessel, Boolean>?) {
            with(bindLayout) {
                data?.let { pair ->
                    val subVessel = pair.first
                    val isLoading = pair.second
                    tvSubArea.text = subVessel.subVesselName
                    if (isLoading) {
                        tvStatus.visible()
                        btnDownload.isLoading = true
                    } else {
                        tvStatus.gone()
                        btnDownload.isLoading = false
                        btnDownload.setOnClickListener {
                            onClickDownload.invoke(subVessel)
                        }
                    }
                }
            }
        }
    }

    inner class FooterViewHolder(val bindLayout: ItemSubAreaLoadMoreBinding) :
        DevItemViewHolder<Pair<SubVessel, Boolean>>(bindLayout) {
        override fun bind(data: Pair<SubVessel, Boolean>?) {
            with(bindLayout) {
                if (footerWithLoading) {
                    btnLoadMore.gone()
                    pbLoader.visible()
                } else {
                    btnLoadMore.visible()
                    pbLoader.gone()
                }
                btnLoadMore.setOnClickListener {
                    onClickLoadMore.invoke()
                }
            }
        }
    }

    private var withFooter = false
    private var footerWithLoading = false

    fun withFooter(value: Boolean) {
        withFooter = value
    }

    fun setFooterWithLoading(value: Boolean) {
        footerWithLoading = value
        notifyItemChanged(itemCount - 1)
    }

    fun setItemDownload(subVessel: SubVessel, isDownload: Boolean = true) {
        val index = listData.map { it?.first }.indexOf(subVessel)
        listData[index] = Pair(subVessel, isDownload)
        notifyItemChanged(index)
    }
}
