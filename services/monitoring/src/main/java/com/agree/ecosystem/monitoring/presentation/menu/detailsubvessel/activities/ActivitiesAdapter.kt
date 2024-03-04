package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ItemAdditionalActivitiesSopBinding
import com.agree.ecosystem.monitoring.databinding.SubItemActivitySopBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.util.getDrawableResource
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding
import com.oratakashi.viewbinding.core.tools.visible

class ActivitiesAdapter(
    onItemClicked: (activitySop: ActivitySop?) -> Unit
) : DevRecyclerViewAdapter<ActivitySop>(onItemClicked) {
    inner class ActivitiesViewHolder(private val bindLayout: SubItemActivitySopBinding) :
        DevItemViewHolder<ActivitySop>(bindLayout) {
        override fun bind(data: ActivitySop?) {
            with(bindLayout) {
                data?.let {
                    tvPhaseName.text = data.phaseName
                    tvActivityName.text = data.getDetailActivity()
                    tvDescription.text = data.description
                    if (data.isCompleted) {
                        imgInformation.setImageDrawable(getDrawableResource(R.drawable.ic_check_circle))
                        imgInformation.visible()
                    } else {
                        imgInformation.gone()
                    }
                }
            }
        }
    }

    inner class AdditionalActivitiesViewHolder(private val bindLayout: ItemAdditionalActivitiesSopBinding) :
        DevItemViewHolder<ActivitySop>(bindLayout) {
        override fun bind(data: ActivitySop?) {
            with(bindLayout) {
                data?.let {
                    tvPhaseName.text = data.phaseName
                    tvActivityName.text = data.getDetailActivity()
                    tvDescription.text = data.description
                    if (data.isCompleted) {
                        imgInformation.setImageDrawable(getDrawableResource(R.drawable.ic_check_circle))
                    } else {
                        imgInformation.gone()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<ActivitySop> {
        return if (viewType == 1) {
            ActivitiesViewHolder(inflateBinding(parent))
        } else {
            AdditionalActivitiesViewHolder(inflateBinding(parent))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listData[position]?.isAdditional == false) {
            1
        } else {
            2
        }
    }
}
