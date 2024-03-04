package com.agree.ecosystem.common.presentation.menu.monitoring.subvessels

import android.view.ViewGroup
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ItemSubvesselBinding
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible

class SubVesselAdapter(
    onClick: (subVessel: SubVessel?) -> Unit,
) : DevRecyclerViewAdapter<SubVessel>(onClickListener = onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DevItemViewHolder<SubVessel> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemSubvesselBinding.inflate(
                    getLayoutInflater(parent),
                    parent,
                    false
                )
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

    inner class ViewHolder(private val bindLayout: ItemSubvesselBinding) :
        DevItemViewHolder<SubVessel>(bindLayout) {
        override fun bind(data: SubVessel?) {
            with(bindLayout) {
                data?.let { subVessel ->
                    val context = root.context
                    tvSubVesselName.text = subVessel.subVesselName
                    if (data.hasSmartfarm) {
                        tvSmartfarmingLabel.visible()
                        ivDottedLine.visible()
                    } else {
                        tvSmartfarmingLabel.gone()
                        ivDottedLine.gone()
                    }
                    val size = "%.2f".format(subVessel.size.toFloat())
                    tvSize.text = context.getString(R.string.label_size, size)

                    tvStatus.apply {
                        text = subVessel.status.status
                        setTextColor(subVessel.status.textColor.getAttrsValue(context))
                    }
                    tvVesselName.text = subVessel.vesselName
                    when (subVessel.subSectorId) {
                        "301", "302" -> {
                            tvCommodityName.apply {
                                text = subVessel.commodityName
                                setTextColor(UiKitAttrs.warning_normal.getAttrsValue(context))
                            }
                            cvStatus.strokeColor = UiKitAttrs.warning_normal.getAttrsValue(context)
                        }
                        "101" -> {
                            tvCommodityName.apply {
                                text = subVessel.commodityName
                                setTextColor(UiKitAttrs.success_normal.getAttrsValue(context))
                            }
                            cvStatus.strokeColor = UiKitAttrs.success_normal.getAttrsValue(context)
                        }
                        else -> {
                            tvCommodityName.apply {
                                text = subVessel.commodityName
                                setTextColor(UiKitAttrs.info_normal.getAttrsValue(context))
                            }
                            cvStatus.strokeColor = UiKitAttrs.info_normal.getAttrsValue(context)
                        }
                    }
                    tvDistrictName.text = subVessel.districtName
                    tvWorkerName.text = subVessel.workerName
                    tvCompanyName.text = subVessel.companyName
                }
            }
        }
    }
}
