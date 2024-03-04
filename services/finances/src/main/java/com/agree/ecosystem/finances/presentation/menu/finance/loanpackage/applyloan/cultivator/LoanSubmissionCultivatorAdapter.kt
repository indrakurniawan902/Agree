package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.cultivator

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.ViewGroup
import com.agree.ecosystem.finances.databinding.ItemLoanSubmissionCheckableCultivatorBinding
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionViewModel
import com.agree.ecosystem.finances.utils.toCheckableCultivator
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getColorResource
import com.oratakashi.viewbinding.core.tools.disable
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible

class LoanSubmissionCultivatorAdapter(
    val onCheckChangedListener: ((LoanSubmissionBaseItem, Boolean, Int) -> Unit)? = null,
    val onEditFarmerOnClick: ((LoanSubmissionBaseItem) -> Unit)? = null,
    val onRequirementsOnClick: ((LoanSubmissionBaseItem) -> Unit)? = null,
    val viewModel: LoanSubmissionViewModel?
) : DevRecyclerViewAdapter<LoanSubmissionBaseItem>() {

    inner class ViewHolder(private val bindLayout: ItemLoanSubmissionCheckableCultivatorBinding) :
        DevItemViewHolder<LoanSubmissionBaseItem>(bindLayout) {
        override fun bind(data: LoanSubmissionBaseItem?) {
            with(bindLayout) {
                data?.let { item ->
                    when (item) {
                        is CheckableCultivator -> {
                            val check = checkLoanEligibility(item)

                            val cur = viewModel?.selectedCultivator?.value
                            if (cur?.isNotEmpty() == true) {
                                cur.map {
                                    if (it.farmerId == item.data.farmerId) {
                                        cbChooseCultivator.isChecked = true
                                    }
                                }
                            }
                            if (check == false) {
                                conditionWhenCannotSubmission(this, item)
                            } else {
                                conditonWhenAllowSelected(this, item, bindingAdapterPosition)
                            }

                            this.tvNameCultivator.text = item.data.name
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<LoanSubmissionBaseItem> {
        return ViewHolder(
            ItemLoanSubmissionCheckableCultivatorBinding.inflate(
                getLayoutInflater(parent),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun updateAt(index: Int, item: LoanSubmissionBaseItem) {
        listData[index] = item
        notifyItemChanged(index)
    }

    fun conditionWhenCannotSubmission(
        binding: ItemLoanSubmissionCheckableCultivatorBinding,
        item: LoanSubmissionBaseItem
    ) {
        with(binding) {
            lyContainerCannotSubmission.visible()
            cbChooseCultivator.disable()
            cbChooseCultivator.buttonTintList =
                ColorStateList.valueOf(getColorResource(com.agree.ui.R.color.tertiary_200))
            tvNameCultivator.setTextColor(getColorResource(com.agree.ui.R.color.tertiary_300))
            tvLookAtTnc.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            btnEdit.gone()
            lyContainerCannotSubmission.setOnClickListener {
                onRequirementsOnClick?.invoke(item)
            }
            this.imgCultivator.loadImage(item.toCheckableCultivator().data.imageBW)
        }
    }

    private fun checkLoanEligibility(
        item: LoanSubmissionBaseItem
    ): Boolean {
        val data = (item as CheckableCultivator).data
        val isEligible = data.isEligible
        return isEligible
    }

    private fun conditonWhenAllowSelected(
        binding: ItemLoanSubmissionCheckableCultivatorBinding,
        item: LoanSubmissionBaseItem,
        adapterPosition: Int
    ) {
        with(binding) {
            cbChooseCultivator.isEnabled = true
            lyContainerCannotSubmission.gone()
            btnEdit.visible()
            this.imgCultivator.loadImage(item.toCheckableCultivator().data.image)
            tvNameCultivator.setTextColor(getColorResource(com.agree.ui.R.color.black))
            cbChooseCultivator.setOnCheckedChangeListener { _, isChecked ->
                onCheckChangedListener?.invoke(item, isChecked, adapterPosition)
            }
            containerCheckableCultivator.setOnClickListener {
                cbChooseCultivator.isChecked = !cbChooseCultivator.isChecked
            }
            btnEdit.setOnClickListener {
                onEditFarmerOnClick?.invoke(item)
            }
        }
    }
}
