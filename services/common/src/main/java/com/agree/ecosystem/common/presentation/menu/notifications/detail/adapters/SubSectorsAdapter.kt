package com.agree.ecosystem.common.presentation.menu.notifications.detail.adapters

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.agree.ecosystem.common.databinding.ItemDataSubSectorInboxBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DataSubSectorsDetailNotification
import com.agree.ecosystem.common.utils.enums.notification.StatesDataDetailInbox
import com.agree.ui.R
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getColorResource

class SubSectorsAdapter : DevRecyclerViewAdapter<DataSubSectorsDetailNotification>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<DataSubSectorsDetailNotification> = ViewHolder(
        ItemDataSubSectorInboxBinding.inflate(getLayoutInflater(parent), parent, false)
    )

    inner class ViewHolder(private val layout: ItemDataSubSectorInboxBinding) :
        DevItemViewHolder<DataSubSectorsDetailNotification>(layout) {
        override fun bind(data: DataSubSectorsDetailNotification?) {
            with(layout) {
                data?.apply {
                    val context = root.context
                    if (data.statusDataSubSector == StatesDataDetailInbox.REJECTED.value) {
                        tvLabelReasonStatus.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.error_500
                            )
                        )
                        cvReasonRejected.strokeColor = getColorResource(R.color.error_500)
                        tvLabelSectorName.text =
                            "${data.sectorNameDataSubSector} ${data.nameDataSubSector}:"
                        tvFieldAssistant.text = data.data.description
                        tvLabelReasonStatus.text =
                            context.getString(com.agree.ecosystem.partnership.R.string.label_reason_status_rejected)
                    }
                    if (data.statusDataSubSector == StatesDataDetailInbox.ASSIGNED.value) {
                        tvLabelReasonStatus.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.success_500
                            )
                        )
                        tvLabelReasonStatus.text =
                            context.getString(com.agree.ecosystem.partnership.R.string.label_reason_status_proses)
                        tvLabelSectorName.text =
                            "${data.sectorNameDataSubSector} ${data.nameDataSubSector}:"
                        tvFieldAssistant.text = root.context.getString(
                            com.agree.ecosystem.common.R.string.label_field_assistant_with_data,
                            data.fieldAssistantDataSubSector
                        )
                    }
                }
            }
        }
    }
}
