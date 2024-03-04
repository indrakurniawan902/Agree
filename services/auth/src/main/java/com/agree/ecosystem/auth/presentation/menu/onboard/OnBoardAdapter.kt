package com.agree.ecosystem.auth.presentation.menu.onboard

import android.view.ViewGroup
import com.agree.ecosystem.auth.databinding.ItemOnboardSliderBinding
import com.agree.ecosystem.auth.domain.reqres.model.onboard.OnBoard
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class OnBoardAdapter : DevRecyclerViewAdapter<OnBoard>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<OnBoard> =
        ViewHolder(ItemOnboardSliderBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemOnboardSliderBinding) :
        DevItemViewHolder<OnBoard>(bindLayout) {
        override fun bind(data: OnBoard?) {
            with(bindLayout) {
                data?.let {
                    imgOnboard.resId = data.image
                    tvHeadline.text = data.headline
                    tvHeadlineDescription.text = data.headlineDescription
                }
            }
        }
    }
}
