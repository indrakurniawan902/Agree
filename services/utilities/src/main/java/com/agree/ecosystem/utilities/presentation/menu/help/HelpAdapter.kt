package com.agree.ecosystem.utilities.presentation.menu.help

import android.view.ViewGroup
import com.agree.ecosystem.utilities.databinding.ItemHelpCategoryBinding
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class HelpAdapter(
    onClick: (help: Help?) -> Unit
) : DevRecyclerViewAdapter<Help>(onClickListener = onClick) {

    inner class ViewHolder(private val bindLayout: ItemHelpCategoryBinding) :
        DevItemViewHolder<Help>(bindLayout) {
        override fun bind(data: Help?) {
            with(bindLayout) {
                data?.let { help ->
                    tvCategory.text = help.name
                }
//                if (layoutPosition == 0) separator.viewSeparator.gone()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Help> {
        return ViewHolder(ItemHelpCategoryBinding.inflate(getLayoutInflater(parent), parent, false))
    }
}
