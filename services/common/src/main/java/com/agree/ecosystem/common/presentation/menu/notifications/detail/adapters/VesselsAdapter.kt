package com.agree.ecosystem.common.presentation.menu.notifications.detail.adapters

import android.view.ViewGroup
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ItemDataVesselsDetailInboxBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.VesselsLastResultRegistration
import com.agree.ecosystem.common.utils.enums.notification.StatesDataDetailInbox
import com.agree.ecosystem.core.utils.utility.extension.setItemSeparator
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.tools.visible

class VesselsAdapter(val subVesselsAdapter: SubVesselsAdapter) :
    DevRecyclerViewAdapter<VesselsLastResultRegistration>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<VesselsLastResultRegistration> = ViewHolder(
        ItemDataVesselsDetailInboxBinding.inflate(
            getLayoutInflater(parent),
            parent,
            false
        )
    )

    inner class ViewHolder(private val layout: ItemDataVesselsDetailInboxBinding) :
        DevItemViewHolder<VesselsLastResultRegistration>(layout) {
        override fun bind(data: VesselsLastResultRegistration?) {
            with(layout) {
                data?.apply {
                    val context = root.context
                    tvTextAreaValue.text =
                        context.getString(R.string.label_size_area, data.size.toString())
                    tvTitleLabel.text = data.vesselName
                    tvTextSurfaceAreaUsedValue.text =
                        context.getString(R.string.label_size_area, data.realizationSize.toString())
                    tvTextLocationValue.text = data.location

                    if (data.status.contains(StatesDataDetailInbox.REJECTED.value, true)) {
                        iLayoutVesselRejectedReason.tvReason.text = data.description
                        iLayoutVesselRejectedReason.cvReasonRejected.visible()
                    }

                    rvSubVessel.apply {
                        adapter = SubVesselsAdapter(data.status).apply {
                            setItemSeparator()
                            clear()
                            addOrUpdateAll(data?.subVessel)
                        }
                    }
                }
            }
        }
    }
}
