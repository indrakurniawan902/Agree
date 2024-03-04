package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.bottomsheetsectorpartnership

import android.view.ViewGroup
import com.agree.ecosystem.finances.databinding.ItemSectorPartnershipBinding
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class SectorPartnershipBottomSheetAdapter(onClick: (subSector: SubSector?) -> Unit) :
    DevRecyclerViewAdapter<SubSector>(onClick) {

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<SubSector> {
        return when (viewType) {
            CONTENT -> ViewHolder(
                ItemSectorPartnershipBinding.inflate(
                    getLayoutInflater(parent),
                    parent, false
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

    inner class ViewHolder(private val bindLayout: ItemSectorPartnershipBinding) :
        DevItemViewHolder<SubSector>(bindLayout) {
        override fun bind(data: SubSector?) {
            with(bindLayout) {
                data?.let {
                    ivIcon.url = it.icon
                    tvName.text = it.getFullSectorName()
                }
            }
        }

    }
}