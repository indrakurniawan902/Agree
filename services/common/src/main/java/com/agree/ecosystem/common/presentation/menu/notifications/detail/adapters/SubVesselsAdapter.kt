package com.agree.ecosystem.common.presentation.menu.notifications.detail.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ItemDataSubVesselsDetailInboxBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.SubVesselsLastResultRegistration
import com.agree.ecosystem.common.utils.enums.notification.StatesDataDetailInbox
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.util.getDrawableResource
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.isNull
import com.oratakashi.viewbinding.core.tools.visible

class SubVesselsAdapter(val statusVessel: String? = "") :
    DevRecyclerViewAdapter<SubVesselsLastResultRegistration?>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<SubVesselsLastResultRegistration?> = ViewHolder(
        ItemDataSubVesselsDetailInboxBinding.inflate(
            getLayoutInflater(parent),
            parent,
            false
        )
    )

    inner class ViewHolder(private val layout: ItemDataSubVesselsDetailInboxBinding) :
        DevItemViewHolder<SubVesselsLastResultRegistration?>(layout) {
        @SuppressLint("SetTextI18n")
        override fun bind(data: SubVesselsLastResultRegistration?) {
            with(layout) {
                data?.apply {
                    val context = root.context
                    tvSubVesselTitle.text = "${data.subvesselName} ${
                    context.getString(
                        R.string.label_size,
                        data.size.toString()
                    )
                    }"
                    tvTextCommodityValue.text = data.commodityName
                    tvTextFarmerValue.text = data.workerName

                    setBackgroundSubVessel(data.sectorName)
                    setStatusRejectedLayout(data)
                    setStatusSubVessel(data.status)
                }
            }
        }

        private fun setBackgroundSubVessel(sectorName: String) {
            with(layout) {
                when (sectorName) {
                    "Peternakan" -> {
                        vSeparatorSubVessel.background =
                            getDrawableResource(com.agree.ui.UiColors.agl_normal)
                    }
                    "Pertanian" -> {
                        vSeparatorSubVessel.background =
                            getDrawableResource(com.agree.ui.UiColors.primary_500)
                    }
                    "Perikanan" -> {
                        vSeparatorSubVessel.background =
                            getDrawableResource(com.agree.ui.UiColors.aqf_normal)
                    }
                }
            }
        }

        private fun setStatusRejectedLayout(data: SubVesselsLastResultRegistration) {
            if (statusVessel.isNull()) {
                return
            }

            with(layout) {
                when {
                    statusVessel?.contains(StatesDataDetailInbox.REJECTED.value, true) == true -> {
                        iLayoutRejectedReason.root.gone()
                    }
                    data.status.contains(StatesDataDetailInbox.REJECTED.value, true) -> {
                        iLayoutRejectedReason.root.visible()
                        iLayoutRejectedReason.tvReason.text = data.description
                    }
                    else -> {
                        iLayoutRejectedReason.root.gone()
                    }
                }
            }
        }

        private fun setStatusSubVessel(status: String) {
            with(layout) {
                when {
                    status.contains(StatesDataDetailInbox.APPROVED.value, true) -> {
                        tvStatus.apply {
                            text =
                                getStringResource(com.agree.ecosystem.partnership.R.string.label_accepted)
                            setTextColor(UiKitAttrs.success_pressed.getAttrsValue(context))
                        }
                        cvStatus.setCardBackgroundColor(UiKitAttrs.success_100.getAttrsValue(root.context))
                    }

                    status.contains(StatesDataDetailInbox.REJECTED.value, true) -> {
                        tvStatus.apply {
                            text =
                                getStringResource(com.agree.ecosystem.partnership.R.string.label_rejected)
                            setTextColor(UiKitAttrs.error_pressed.getAttrsValue(context))
                        }
                        cvStatus.setCardBackgroundColor(UiKitAttrs.error_100.getAttrsValue(root.context))
                    }
                }
            }
        }
    }
}
