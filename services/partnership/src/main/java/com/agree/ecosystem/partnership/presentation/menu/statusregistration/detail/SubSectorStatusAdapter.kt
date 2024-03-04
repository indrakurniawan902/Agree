package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import android.view.ViewGroup
import androidx.core.view.isGone
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemSectorStatusBinding
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getColorResource
import com.devbase.utils.util.getStringResource

class SubSectorStatusAdapter(private var isAllRejected: Boolean = false) : DevRecyclerViewAdapter<SubSector>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<SubSector> =
        ViewHolder(ItemSectorStatusBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemSectorStatusBinding) :
        DevItemViewHolder<SubSector>(bindLayout) {
        override fun bind(data: SubSector?) {
            with(bindLayout) {
                data?.let {
                    when (it.scheduleStatus) {
                        "assigned", "processed", "done" -> {
                            cvReasonRejected.strokeColor = getColorResource(com.agree.ui.R.color.success_500)
                            tvLabelReason.text = "${it.getFullSectorName()}:"
                            tvLabelReasonStatus.setTextColor(getColorResource(com.agree.ui.R.color.primary_500))
                            tvLabelReasonStatus.text =
                                getStringResource(R.string.label_reason_status_proses)
                            tvReason.text = root.context.getString(R.string.label_field_assistant_name, it.fieldAssistantName)
                        }
                        "rejected" -> {
                            cvReasonRejected.strokeColor = getColorResource(com.agree.ui.R.color.error_500)
                            tvLabelReason.text = "${it.getFullSectorName()}:"
                            tvLabelReasonStatus.setTextColor(getColorResource(com.agree.ui.R.color.error_500))
                            tvLabelReasonStatus.text =
                                getStringResource(R.string.label_reason_status_rejected)
                            tvReason.apply {
                                isGone = isAllRejected
                                text = it.scheduleDescription
                            }
                        }
                        else -> {
                            // Do Nothing
                        }
                    }
                }
            }
        }
    }
}
