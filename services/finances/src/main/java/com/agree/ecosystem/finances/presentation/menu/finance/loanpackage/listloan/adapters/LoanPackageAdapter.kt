package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.adapters

import android.view.ViewGroup
import com.agree.ecosystem.finances.databinding.ItemLoanPackageOfCompanyBinding
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackageCompany
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackagePaymentType
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.tools.onClick
import timber.log.Timber

class LoanPackageAdapter(
    val onClick: (loanPackage: LoanPackageCompany?) -> Unit
) : DevRecyclerViewAdapter<LoanPackageCompany>(onClickListener = onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<LoanPackageCompany> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemLoanPackageOfCompanyBinding.inflate(
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

    inner class ViewHolder(private val bindLayout: ItemLoanPackageOfCompanyBinding) :
        DevItemViewHolder<LoanPackageCompany>(bindLayout) {
        override fun bind(data: LoanPackageCompany?) {
            with(bindLayout) {

                data?.let { dataItem ->
                    imgListLoan.url = dataItem.image
                    tvTypeLoanPackage.text = when (dataItem.loanPackagePaymentType) {
                        LoanPackagePaymentType.TUNAI.label -> LoanPackagePaymentType.TUNAI.value
                        else -> {
                            LoanPackagePaymentType.NONTUNAI.value
                        }
                    }
                    this.root.onClick {
                        onClick.invoke(data)
                    }
                }
            }
        }
    }
}
