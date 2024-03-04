package com.agree.ecosystem.finances.presentation.menu.finance.loanactive

import android.view.ViewGroup
import com.agree.ecosystem.finances.databinding.ItemLoanActiveBinding
import com.agree.ecosystem.finances.domain.reqres.model.MyLoanData
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class ListLoanActiveAdapter(
    private val onItemClicked: ((loanActive: MyLoanData?) -> Unit)
) : DevRecyclerViewAdapter<MyLoanData>(onItemClicked) {

    inner class ViewHodler(private val bindLayout: ItemLoanActiveBinding) :
        DevItemViewHolder<MyLoanData>(bindLayout) {
        override fun bind(data: MyLoanData?) {
            with(bindLayout) {
                data?.let { item ->
                    tvLoanActive.text = item.isActive.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<MyLoanData> {
        return when (viewType) {
            CONTENT -> ViewHodler(
                ItemLoanActiveBinding.inflate(
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

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE
}
