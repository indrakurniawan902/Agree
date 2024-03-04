package com.agree.ecosystem.monitoring.presentation.menu.settingsoffline

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemSubAreaDownloadedBinding
import com.agree.ecosystem.monitoring.databinding.ItemSubAreaLoadMoreBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible

class SettingsOfflineMonitoringDownloadedAdapter(
    val onClickRemove: (SubVessel) -> Unit,
    val onClickLoadMore: () -> Unit
) : DevRecyclerViewAdapter<SubVessel>() {

    override fun onBindViewHolder(
        holder: DevItemViewHolder<SubVessel>,
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
    ): DevItemViewHolder<SubVessel> =
        when (viewType) {
            LOAD_MORE -> FooterViewHolder(
                ItemSubAreaLoadMoreBinding.inflate(
                    getLayoutInflater(parent),
                    parent,
                    false
                )
            )

            else -> ViewHolder(
                ItemSubAreaDownloadedBinding.inflate(
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

    inner class ViewHolder(private val bindLayout: ItemSubAreaDownloadedBinding) :
        DevItemViewHolder<SubVessel>(bindLayout) {
        override fun bind(data: SubVessel?) {
            with(bindLayout) {
                data?.let { subVessel ->
                    tvSubArea.text = subVessel.subVesselName
                    btnRemove.setOnClickListener {
                        onClickRemove.invoke(subVessel)
                    }
                }
            }
        }
    }

    inner class FooterViewHolder(val bindLayout: ItemSubAreaLoadMoreBinding) :
        DevItemViewHolder<SubVessel>(bindLayout) {
        override fun bind(data: SubVessel?) {
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
}
