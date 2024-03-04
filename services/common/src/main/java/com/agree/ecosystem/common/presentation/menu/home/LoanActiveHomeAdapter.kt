package com.agree.ecosystem.common.presentation.menu.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ItemLoanActiveHomeBinding
import com.agree.ecosystem.common.databinding.ItemMoreListLoanActiveHomePageBinding
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoan
import com.agree.ecosystem.core.utils.utility.convertUTC3TimeTo
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.util.getStringResource

class LoanActiveHomeAdapter(
    onClick: (myLoan: MyLoan?) -> Unit,
) : DevRecyclerViewAdapter<MyLoan>(onClickListener = onClick) {

    inner class DataViewHolder(
        private val bindingLayout: ItemLoanActiveHomeBinding
    ) : DevItemViewHolder<MyLoan>(bindingLayout) {

        fun removeAngleBracket(value: String): String {
            return value.replace("[", "").replace("]", "")
        }

        override fun bind(data: MyLoan?) {
            with(bindingLayout) {
                data?.let {
                    tvLoanPackageName.text = it.loanPackageName
                    tvDateOfSubmission.text =
                        it.loanSubmissionDateTime.convertUTC3TimeTo(com.agree.ecosystem.core.utils.utility.ConverterDate.SIMPLE_DAY_DATE)
                    val submissioner =
                        it.loanBorrowers.map { it }.toString().split(",")
                    if (submissioner.size > 4) {
                        tvCultivatorOfSubmission.text =
                            removeAngleBracket(submissioner.subList(0, 3).toString()).plus("...")
                    } else {
                        tvCultivatorOfSubmission.text =
                            removeAngleBracket(submissioner.toString())
                        tvMoreCultivatorOfSubmission.gone()
                    }
                }
            }
        }
    }

    inner class LoanMoreActiveViewHolder(private val bindLayout: ItemMoreListLoanActiveHomePageBinding) :
        DevItemViewHolder<MyLoan>(bindLayout) {
        override fun bind(data: MyLoan?) {
            with(bindLayout) {
                this.tvLabel.text =
                    getStringResource(R.string.label_see_more_loan_active_home_page)
                (this.root.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    if (layoutPosition == 4) rightMargin = 0
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listData[position]?.loanId == "") {
            R.layout.item_more_list_loan_active_home_page
        } else {
            R.layout.item_loan_active_home
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<MyLoan> {
        return when (viewType) {
            R.layout.item_loan_active_home -> DataViewHolder(
                ItemLoanActiveHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            else -> {
                LoanMoreActiveViewHolder(
                    ItemMoreListLoanActiveHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
        }
    }
}
