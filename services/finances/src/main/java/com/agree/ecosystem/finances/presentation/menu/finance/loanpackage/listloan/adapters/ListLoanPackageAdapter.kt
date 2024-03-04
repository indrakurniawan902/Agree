package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.adapters

import android.view.ViewGroup
import com.agree.ecosystem.finances.databinding.ItemLoanPackgeBinding
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackage
import com.agree.ecosystem.finances.presentation.navigation.MenuNavigation
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class ListLoanPackageAdapter(val nav: MenuNavigation) : DevRecyclerViewAdapter<LoanPackage>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<LoanPackage> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemLoanPackgeBinding.inflate(
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

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemLoanPackgeBinding) :
        DevItemViewHolder<LoanPackage>(bindLayout) {

        override fun bind(data: LoanPackage?) {
            val mapSubSector: ArrayList<String> = arrayListOf()
            data?.subsector?.forEach {
                mapSubSector.add("${it.sectorName} ${it.subSectorName}")
            }
            with(bindLayout) {
                data?.let { loanPackage ->
                    ivCompanyLogo.url = loanPackage.companyImage
                    tvPartnerCompany.text = loanPackage.companyName
                    tvSectorCompanyPartner.text = mapSubSector.map { it }.toString()
                        .replace("[", "")
                        .replace("]", "")
//                    loanPackageAdapter.apply {
//                        loanPackageAdapter.addOrUpdateAll(it.loanPackage)
//                    }
                    rvLoanPackage.adapter = LoanPackageAdapter(
                        { loanPackageCompany ->
                            nav.fromListPackageToDetailPackage(
                                loanPackageId = loanPackageCompany?.loanPackageId ?: "",
                                companyId = loanPackage.companyId,
                                mitraId = loanPackage.mitraId
                            )
                        }
                    ).apply {
                        addOrUpdateAll(loanPackage.loanPackage)
                    }
                }
            }
        }
    }
}
