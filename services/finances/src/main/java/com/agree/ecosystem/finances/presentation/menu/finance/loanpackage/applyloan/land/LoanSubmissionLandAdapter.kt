package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.land

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertTimeToNewTimeFormat
import com.agree.ecosystem.core.utils.utility.setNumberFormatter
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ItemLoanSubmissionCheckableLandBinding
import com.agree.ecosystem.finances.databinding.ItemLoanSubmissionDropdownLandBinding
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableLand
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.DropdownLand
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionViewModel
import com.agree.ecosystem.finances.utils.LoanSubmissionItemType
import com.agree.ecosystem.finances.utils.formatCurrency
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getDrawableResource
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible

class LoanSubmissionLandAdapter(
    val onCheckChangedOnClick: ((LoanSubmissionBaseItem, Boolean, Int) -> Unit)? = null,
    val onBudgetPlanClicker: ((LoanSubmissionBaseItem) -> Unit)? = null,
    val viewModel: LoanSubmissionViewModel?
) : DevRecyclerViewAdapter<LoanSubmissionBaseItem>() {

    override fun getItemViewType(position: Int): Int {
        return listData[position]?.viewType?.layoutRes ?: 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<LoanSubmissionBaseItem> {
        return when (viewType) {
            LoanSubmissionItemType.DROPDOWN_LAND.layoutRes ->
                ViewHolderCultivatorLand(
                    ItemLoanSubmissionDropdownLandBinding.inflate(
                        getLayoutInflater(parent), parent, false
                    )
                )

            else -> {
                ViewHolderCultivatorLand(
                    ItemLoanSubmissionCheckableLandBinding.inflate(
                        getLayoutInflater(parent), parent, false
                    )
                )
            }
        }
    }

    inner class ViewHolderCultivatorLand(
        val bindLayout: ViewBinding
    ) : DevItemViewHolder<LoanSubmissionBaseItem>(bindLayout) {

        override fun bind(data: LoanSubmissionBaseItem?) {
            with(bindLayout) {
                data?.let { item ->
                    when (item) {
                        is DropdownLand -> {
                            this as ItemLoanSubmissionDropdownLandBinding
                            viewModel?.registerDropdownElement(
                                item.data.farmerId,
                                Triple(llLandCount, tvLandCount, item.isExpanded)
                            )

                            root.background = getDrawableResource(com.agree.ui.R.color.tertiary_25)

                            val count = viewModel?.selectedLand?.value?.filter {
                                it.farmerId == item.data.farmerId
                            }?.size ?: 0

                            if (count <= 0) {
                                viewModel?.dropdownLandElements?.value?.get(item.data.farmerId)?.first?.gone()
                            } else {
                                viewModel?.dropdownLandElements?.value?.get(item.data.farmerId)?.first?.visible()
                                viewModel?.dropdownLandElements?.value?.get(item.data.farmerId)?.second?.text =
                                    binding.root.resources.getString(
                                        R.string.label_selected_land,
                                        count.toString()
                                    )
                            }

                            tvNameCultivator.text = item.data.name
                            tvValueNIK.text = item.data.nik
                            imgCultivator.url = item.data.image
                            imgCollapse.rotation = if (item.isExpanded) 0f else 180f

                            containerDropdownCultivator.setOnClickListener {
                                item.isExpanded = !item.isExpanded
                                if (item.isExpanded) {
                                    addMultipleAt(
                                        item.dropdownData,
                                        bindingAdapterPosition + 1
                                    )
                                    imgCollapse.rotation = 0f
                                    root.background =
                                        getDrawableResource(com.agree.ui.R.color.tertiary_25)

                                } else {
                                    imgCollapse.rotation = 180f
                                    removeAt(
                                        bindingAdapterPosition + 1,
                                        bindingAdapterPosition + 1 + item.dropdownData.size,
                                        item.dropdownData.size
                                    )
                                    root.background =
                                        getDrawableResource(com.agree.ui.R.color.white)
                                }
                            }

                            imgCollapse.setOnClickListener {
                                item.isExpanded = !item.isExpanded
                                if (item.isExpanded) {
                                    addMultipleAt(
                                        item.dropdownData,
                                        bindingAdapterPosition + 1
                                    )
                                    it.rotation = 0f
                                    root.background =
                                        getDrawableResource(com.agree.ui.R.color.tertiary_25)
                                } else {
                                    it.rotation = 180f
                                    removeAt(
                                        bindingAdapterPosition + 1,
                                        bindingAdapterPosition + 1 + item.dropdownData.size,
                                        item.dropdownData.size
                                    )
                                    root.background =
                                        getDrawableResource(com.agree.ui.R.color.white)
                                }
                            }
                        }

                        is CheckableLand -> {
                            this as ItemLoanSubmissionCheckableLandBinding

                            root.background = getDrawableResource(com.agree.ui.R.color.tertiary_25)

                            cbChooseLand.setOnCheckedChangeListener(null)
                            containerCheckableLand.setOnClickListener(null)

                            val cur = viewModel?.selectedLand?.value

                            if (viewModel?.dropdownLandElements?.value?.get(item.farmerId)?.third == true) {
                                root.background =
                                    getDrawableResource(com.agree.ui.R.color.tertiary_25)
                            } else {
                                root.background =
                                    getDrawableResource(com.agree.ui.R.color.tertiary_25)
                            }

                            if (cur?.isNotEmpty() == true) {
                                cur.map {
                                    if (it.data.landId == item.data.landId) {
                                        cbChooseLand.isChecked = true
                                    }
                                }
                            }

                            tvLandName.text = item.data.landName
                            tvValueSurfaceAre.text = root.resources.getString(
                                R.string.label_land_realization,
                                item.data.size.toString().setNumberFormatter(),
                                item.data.size.toString().setNumberFormatter()
                            )
                            tvValuePlantSeason.text =
                                if (item.data.plantingSeasonStartDate.isNotBlank()) {
                                    item.data.plantingSeasonStartDate.convertTimeToNewTimeFormat(
                                        ConverterDate.UTC3, ConverterDate.SHORT_DATE
                                    )
                                } else {
                                    getStringResource(R.string.label_not_started_yet)
                                }

                            tvValueBudgetPlan.text = root.resources.getString(
                                R.string.label_cultivator_total_land_RAB,
                                formatCurrency(
                                    item.data.budgetPlanTotalPrice.toDoubleOrNull()
                                        ?: (0.0 * item.data.size)
                                )
                            )

                            containerCheckableLand.setOnClickListener {
                                cbChooseLand.isChecked = !cbChooseLand.isChecked
                            }

                            cbChooseLand.setOnCheckedChangeListener { _, value ->
                                if (value) {
                                    showAndIncrementCount(item)
                                } else {
                                    showAndDecrementCount(item)
                                }
                                onCheckChangedOnClick?.invoke(item, value, bindingAdapterPosition)
                                item.isChecked = value
                            }

                            tvLabelLookAtBudgetPlan.setOnClickListener {
                                onBudgetPlanClicker?.invoke(item)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

        private fun showAndIncrementCount(item: CheckableLand) {
            var count = filterData(item)
            viewModel?.incrementCount(item, count++)
            bindLandCount(item.farmerId, count, false)
        }

        private fun filterData(item: CheckableLand): Int {
            return viewModel?.selectedLand?.value?.filter {
                it.farmerId == item.farmerId
            }?.size ?: 0
        }

        private fun bindLandCount(farmerId: String, count: Int, isNull: Boolean) {
            if (isNull == true) {
                viewModel?.dropdownLandElements?.value?.get(farmerId)?.first?.gone()
            } else {
                viewModel?.dropdownLandElements?.value?.get(farmerId)?.first?.visible()
            }

            viewModel?.dropdownLandElements?.value?.get(farmerId)?.second?.text =
                binding.root.resources.getString(R.string.label_selected_land, count.toString())
        }

        private fun showAndDecrementCount(item: CheckableLand) {
            var count = filterData(item)
            viewModel?.decrementCount(item, count--)
            if (count <= 0) bindLandCount(item.farmerId, count, true)
        }

        private fun addMultipleAt(
            data: List<LoanSubmissionBaseItem>,
            startIndex: Int
        ) {
            listData.addAll(startIndex, data)
            notifyItemRangeInserted(startIndex, data.size)
        }

        private fun removeAt(startIndex: Int, endIndex: Int, itemCount: Int) {
            if (itemCount > 0) {
                listData.subList(startIndex, endIndex).clear()
                notifyItemRangeRemoved(startIndex, itemCount)
            }
        }

        fun updateAt(index: Int, item: LoanSubmissionBaseItem) {
            listData[index] = item
            notifyItemChanged(index)
        }
    }
}