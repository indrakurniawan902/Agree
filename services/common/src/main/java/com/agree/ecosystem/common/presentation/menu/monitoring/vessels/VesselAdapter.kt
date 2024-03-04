package com.agree.ecosystem.common.presentation.menu.monitoring.vessels

import android.view.ViewGroup
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ItemVesselBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class VesselAdapter(
    onClick: (vessel: Vessel?) -> Unit,
) : DevRecyclerViewAdapter<Vessel>(onClickListener = onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DevItemViewHolder<Vessel> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemVesselBinding.inflate(
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

    inner class ViewHolder(private val bindLayout: ItemVesselBinding) :
        DevItemViewHolder<Vessel>(bindLayout) {
        override fun bind(data: Vessel?) {
            with(bindLayout) {
                data?.let { vessel ->
                    val context = root.context
                    tvVesselName.text = vessel.name
                    tvLocationArea.text =
                        context.getString(R.string.label_location_area, vessel.districtName, vessel.size.toString())
                }
            }
        }
    }
}
