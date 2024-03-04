package com.agree.ecosystem.partnership.presentation.menu.detailvessel

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemSubVesselBinding
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getStringResource

class DetailVesselAdapter(private val onItemClick: (SubVessel) -> Unit) :
    DevRecyclerViewAdapter<SubVessel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<SubVessel> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemSubVesselBinding.inflate(getLayoutInflater(parent), parent, false)
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

    inner class ViewHolder(private val bindLayout: ItemSubVesselBinding) :
        DevItemViewHolder<SubVessel>(bindLayout) {
        override fun bind(data: SubVessel?) {
            with(bindLayout) {
                data?.let { subVessel ->
                    val context = root.context
                    tvSubVesselName.text = subVessel.subVesselName
                    tvSubVesselName.append(
                        SpannableString(" (${context.getString(R.string.label_farm_size_value, subVessel.size.toDouble())})").apply {
                            setSpan(
                                ForegroundColorSpan(
                                    UiKitAttrs.colorPrimary.getAttrsValue(context)
                                ),
                                0, this.length, 0
                            )
                        }
                    )
                    tvSubVesselId.text = subVessel.code
                    tvLocation.text = subVessel.districtName
                    tvCommodities.text = subVessel.commodityName
                    if (subVessel.status == SubVessel.Status.ACTIVE) {
                        tvStatus.apply {
                            text = getStringResource(R.string.label_active)
                            setTextColor(UiKitAttrs.success_normal.getAttrsValue(context))
                        }
                        cvStatus.strokeColor = UiKitAttrs.success_normal.getAttrsValue(context)
                    } else {
                        tvStatus.apply {
                            text = getStringResource(R.string.label_active)
                            setTextColor(UiKitAttrs.black_600.getAttrsValue(context))
                        }
                        cvStatus.strokeColor = UiKitAttrs.black_600.getAttrsValue(context)
                    }
                    root.setOnClickListener {
                        onItemClick(subVessel)
                    }
                }
            }
        }
    }
}
