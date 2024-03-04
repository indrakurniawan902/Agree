package com.agree.ecosystem.partnership.presentation.menu.companies.detail

import android.view.ViewGroup
import com.agree.ecosystem.partnership.databinding.ItemSubSectorsBinding
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding

class DetailCompanyCommodityAdapter : DevRecyclerViewAdapter<Commodity>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<Commodity> = ViewHolder(
        inflateBinding(parent)
    )

    inner class ViewHolder(private val bindLayout: ItemSubSectorsBinding) :
        DevItemViewHolder<Commodity>(bindLayout) {
        override fun bind(data: Commodity?) {
            with(bindLayout) {
                data?.let { commodity ->
                    imgSubCommodity.url = commodity.image
                    tvLabelSubCommodity.text = commodity.name
                }
            }
        }
    }
}
