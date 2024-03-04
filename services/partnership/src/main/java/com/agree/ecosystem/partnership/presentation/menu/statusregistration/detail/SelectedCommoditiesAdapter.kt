package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import android.view.ViewGroup
import com.agree.ecosystem.partnership.databinding.ItemSelectedCommodityBinding
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class SelectedCommoditiesAdapter : DevRecyclerViewAdapter<Commodity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Commodity> =
        ViewHolder(ItemSelectedCommodityBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemSelectedCommodityBinding) :
        DevItemViewHolder<Commodity>(bindLayout) {
        override fun bind(data: Commodity?) {
            with(bindLayout) {
                data?.let { commodity ->
                    tvCommodity.text = commodity.name
                }
            }
        }
    }
}
