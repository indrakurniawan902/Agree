package com.agree.ecosystem.partnership.presentation.menu.registration

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.agree.ecosystem.partnership.databinding.ItemCommoditiesInfoBinding
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class RegisterCommoditiesAdapter : DevRecyclerViewAdapter<Commodity>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Commodity> {
        return ViewHolder(
            ItemCommoditiesInfoBinding.inflate(getLayoutInflater(parent), parent, false)
        )
    }

    inner class ViewHolder(private val bindLayout: ItemCommoditiesInfoBinding) :
        DevItemViewHolder<Commodity>(bindLayout) {
        @SuppressLint("ResourceAsColor")
        override fun bind(data: Commodity?) {
            with(bindLayout) {
                tvCommodities.text = data!!.name
            }
        }
    }
}
