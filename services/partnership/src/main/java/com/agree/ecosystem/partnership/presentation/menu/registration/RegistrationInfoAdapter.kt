package com.agree.ecosystem.partnership.presentation.menu.registration

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemSubSectorInfoBinding
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getStringResource

class RegistrationInfoAdapter : DevRecyclerViewAdapter<SubSector>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<SubSector> {
        return ViewHolder(
            ItemSubSectorInfoBinding.inflate(getLayoutInflater(parent), parent, false)
        )
    }

    inner class ViewHolder(private val bindLayout: ItemSubSectorInfoBinding) :
        DevItemViewHolder<SubSector>(bindLayout) {
        @SuppressLint("ResourceAsColor")
        override fun bind(data: SubSector?) {
            with(bindLayout) {
                tvTitleSubSector.text = "${getStringResource(R.string.label_subsector)} ${data?.name}"
                val childAdapter = RegisterCommoditiesAdapter()
                childAdapter.addAll(data?.commodities)
                rvCommodities.adapter = childAdapter
            }
        }
    }
}
