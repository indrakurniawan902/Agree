package com.agree.ecosystem.partnership.presentation.menu.companies

import android.view.ViewGroup
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemCompanyPartnerBinding
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class CompaniesAdapter(
    private val onItemClicked: (company: Company) -> Unit
) : DevRecyclerViewAdapter<Company>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Company> =
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
        DevItemViewHolder<Company>(bindLayout) {
        override fun bind(data: Company?) {
            with(bindLayout) {
                data?.let { company ->
                    tvCompanyName.text = company.name
                    tvCommodities.text = company.subSectors.joinToString { it.getSubSectorLabel() }
                    tvLocation.text = if (company.isArea) {
                        company.scopeAreas.joinToString { it.provinceName }
                    } else {
                        root.context.getString(R.string.label_all_indonesia)
                    }
                    ivCompanyLogo.url = company.image
                    root.setOnClickListener {
                        onItemClicked(company)
                    }
                    btnNext.setOnClickListener {
                        onItemClicked(company)
                    }
                }
            }
        }
    }
}
