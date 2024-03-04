package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator

import android.view.ViewGroup
import com.agree.ecosystem.finances.databinding.ItemCultivatorMemberBinding
import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class ListCultivatorAdapter(
    onItemClicked: (cultivator: CultivatorBorrower?) -> Unit
) : DevRecyclerViewAdapter<CultivatorBorrower>(onClickListener = onItemClicked) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<CultivatorBorrower> {
        return when (viewType) {
            CONTENT -> ViewHolder(
                ItemCultivatorMemberBinding.inflate(
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
    }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemCultivatorMemberBinding) :
        DevItemViewHolder<CultivatorBorrower>(bindLayout) {
        override fun bind(data: CultivatorBorrower?) {
            with(bindLayout) {
                data?.let { cultivatorBorrower ->
                    tvCultivatorName.text = cultivatorBorrower.name
                    tvValueNik.text = cultivatorBorrower.nik
                    ivCultivatorPhoto.url = cultivatorBorrower.image
                }
            }
        }
    }
}
