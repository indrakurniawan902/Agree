package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.nominal

import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ItemLoanSubmissionNominalBinding
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.NominalCultivator
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionViewModel
import com.agree.ecosystem.finances.utils.addCurrencyTextWatcher
import com.agree.ecosystem.finances.utils.formatCurrency
import com.agree.ecosystem.finances.utils.parseCurrencyToLong
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class LoanSubmissionNominalAdapter(
    val onNominalChanged: ((LoanSubmissionBaseItem, Int) -> Unit)? = null,
    val viewModel: LoanSubmissionViewModel?
) : DevRecyclerViewAdapter<LoanSubmissionBaseItem>() {

    override fun getItemViewType(position: Int): Int {
        return listData[position]?.viewType?.layoutRes ?: 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DevItemViewHolder<LoanSubmissionBaseItem> {
        return ViewHolderNominal(
            ItemLoanSubmissionNominalBinding.inflate(
                getLayoutInflater(parent), parent, false
            )
        )
    }

    inner class ViewHolderNominal(
        val bindLayout: ViewBinding
    ) : DevItemViewHolder<LoanSubmissionBaseItem>(bindLayout) {
        override fun bind(data: LoanSubmissionBaseItem?) {
            with(bindLayout) {
                data?.let { item ->
                    when (item) {
                        is NominalCultivator -> {
                            this as ItemLoanSubmissionNominalBinding
                            setIsRecyclable(false)

                            val value =
                                viewModel?.nominal?.value?.get(bindingAdapterPosition).toString()
                            initBinding(item, this)

                            if (value == "null" || value == "[]") {
                                lgnSingleField.editText?.setText("")
                            } else {
                                lgnSingleField.editText?.setText(value)
                            }

                            if (item.showError) {
                                this.lgnSingleField.error = item.errorText
                                this.lgnSingleField.startIconDrawable = null
                            } else {
                                lgnSingleField.error = ""
                                lgnSingleField.startIconDrawable = null
                            }

                            lgnSingleField.addCurrencyTextWatcher()
                            lgnSingleField.editText?.doOnTextChanged { text, _, _, _ ->
                                item.nominal = text.toString()
                                when {
                                    text.toString()
                                        .parseCurrencyToLong() <= item.data.loanPackageMaxAmount && text.toString()
                                        .parseCurrencyToLong() >= item.data.loanPackageMinAmount -> {
                                        item.errorText =
                                            "Maksimal Rp${formatCurrency(item.data.loanPackageMaxAmount)}"
                                        lgnSingleField.error = item.errorText
                                        item.showError = true
                                    }

                                    text.toString()
                                        .parseCurrencyToLong() < item.data.loanPackageMinAmount -> {
                                        item.errorText =
                                            "Minimal Rp${formatCurrency(item.data.loanPackageMinAmount)}"
                                        lgnSingleField.error = item.errorText
                                        item.showError = true
                                    }

                                    text.toString()
                                        .parseCurrencyToLong() > item.data.loanPackageMaxAmount -> {
                                        lgnSingleField.editText?.setText(formatCurrency(item.data.loanPackageMaxAmount))
                                    }

                                    else -> {
                                        lgnSingleField.error = ""
                                        item.showError = false
                                    }
                                }
                                onNominalChanged?.invoke(item, bindingAdapterPosition)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun initBinding(
        item: NominalCultivator, viewBinding: ItemLoanSubmissionNominalBinding
    ) {
        with(viewBinding) {
            item.errorText = root.resources.getString(
                R.string.label_minimum_nominal, formatCurrency(item.data.loanPackageMinAmount)
            )
            item.showError = true

            if (item.data.image.isNotEmpty() == true) {
                imgCultivator.loadImage(item.data.image)
            } else {
                imgCultivator.loadImage(item.data.imageBW)
            }
            tvCultivatorName.text = item.data.name
        }
    }

    fun initData(data: List<LoanSubmissionBaseItem?>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }
}
