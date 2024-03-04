package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction

import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC2TimeTo
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.toCurrencyFormat
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ItemTransactionBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class TransactionAdapter(
    onClick: (transaction: Transaction?) -> Unit,
) : DevRecyclerViewAdapter<Transaction>(onClickListener = onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<Transaction> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemTransactionBinding.inflate(
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

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemTransactionBinding) :
        DevItemViewHolder<Transaction>(bindLayout) {
        override fun bind(data: Transaction?) {
            with(bindLayout) {
                data?.let { transaction ->
                    val context = root.context
                    tvDate.text = transaction.dateTime.convertUTC2TimeTo(ConverterDate.FULL_DATE)
                    tvNominal.text = context.getString(R.string.label_rupiah_value, if (transaction.fee == 0L) "-" else transaction.fee.toDouble().toCurrencyFormat())
                    tvWeight.text = context.getString(R.string.label_weight_value, if (data.netto.isNotEmpty()) (data.netto.toDouble() * 1000).toString() else "-")
                    tvType.text = transaction.productType
                    if (transaction.status == Transaction.Status.IN_PROGRESS) {
                        tvStatus.apply {
                            text = context.getString(R.string.label_inprogress)
                            setTextColor(UiKitAttrs.warning_normal.getAttrsValue(context))
                        }
                        cvStatus.strokeColor = UiKitAttrs.warning_normal.getAttrsValue(context)
                    } else {
                        tvStatus.apply {
                            text = context.getString(R.string.label_done)
                            setTextColor(UiKitAttrs.success_hover.getAttrsValue(context))
                        }
                        cvStatus.strokeColor = UiKitAttrs.success_hover.getAttrsValue(context)
                    }
                }
            }
        }
    }
}
