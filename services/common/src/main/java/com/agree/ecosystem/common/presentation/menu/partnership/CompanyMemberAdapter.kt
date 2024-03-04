package com.agree.ecosystem.common.presentation.menu.partnership

import android.view.ViewGroup
import com.agree.ecosystem.partnership.databinding.ItemCompanyPartnerBinding
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMember
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone

class CompanyMemberAdapter(
    private val onItemClicked: (companyMember: CompanyMember) -> Unit
) : DevRecyclerViewAdapter<CompanyMember>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<CompanyMember> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemCompanyPartnerBinding.inflate(getLayoutInflater(parent), parent, false)
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

    inner class ViewHolder(private val bindLayout: ItemCompanyPartnerBinding) :
        DevItemViewHolder<CompanyMember>(bindLayout) {
        override fun bind(data: CompanyMember?) {
            with(bindLayout) {
                data?.let { company ->
                    tvCompanyName.text = company.companyName
                    tvCommodities.text = if (company.commodities.isNotEmpty()) {
                        company.commodities.joinToString { it.name }
                    } else {
                        "-"
                    }
                    tvLocation.gone()
                    ivCompanyLogo.url = company.avatar
                    root.setOnClickListener {
                        onItemClicked(company)
                    }
                }
            }
        }
    }
}
