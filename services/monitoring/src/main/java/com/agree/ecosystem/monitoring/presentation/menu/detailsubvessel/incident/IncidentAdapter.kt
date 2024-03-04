package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC2TimeTo
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ItemIncidentBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible

class IncidentAdapter(
    onClick: (incident: Incident?) -> Unit,
) : DevRecyclerViewAdapter<Incident>(onClickListener = onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<Incident> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemIncidentBinding.inflate(
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

    inner class ViewHolder(private val bindLayout: ItemIncidentBinding) :
        DevItemViewHolder<Incident>(bindLayout) {
        override fun bind(data: Incident?) {
            with(bindLayout) {
                data?.let { incident ->
                    val context = root.context
                    tvDate.text = incident.dateTime.convertUTC2TimeTo(ConverterDate.FULL_DATE)
                    tvDisease.text = incident.category
                    tvDiseaseDetail.text = incident.name
                    if (incident.name.isNotEmpty()) {
                        tvDiseaseDetail.visible()
                    } else
                        tvDiseaseDetail.gone()

                    if (incident.status == context.getString(R.string.label_pending) || incident.status == context.getString(
                            R.string.label_processed
                        )
                    ) {
                        tvStatus.apply {
                            text = context.getString(R.string.label_inprogress)
                            setTextColor(UiKitAttrs.warning_normal.getAttrsValue(context))
                        }
                        cvStatus.strokeColor = UiKitAttrs.warning_normal.getAttrsValue(context)
                    } else {
                        tvStatus.apply {
                            text = context.getString(R.string.label_done)
                            setTextColor(UiKitAttrs.success_hover.getAttrsValue(context))
                        }
                        cvStatus.strokeColor = UiKitAttrs.success_hover.getAttrsValue(context)
                    }
                }
            }
        }
    }
}
