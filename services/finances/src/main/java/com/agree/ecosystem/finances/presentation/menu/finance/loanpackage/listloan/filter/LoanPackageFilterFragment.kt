package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.R
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.finances.databinding.FragmentLoanPackageFilterBinding
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import com.telkom.legion.component.viewstate.showDefaultLayout

class LoanPackageFilterFragment(
    private val datas: List<FinanceCustomMapData>,
    private val defaultValue: String,
    private val callBack: (List<FinanceCustomMapData>) -> Unit
) : AgrBottomSheet() {

    private val binding: FragmentLoanPackageFilterBinding by viewBinding()
    private val itemsFilter: MutableList<FinanceCustomMapData> by lazy {
        ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun initData() {
        super.initData()
        itemsFilter.clear()
        itemsFilter.addAll(datas)
        init(itemsFilter)
    }

    private fun init(data: List<FinanceCustomMapData>) {
        with(binding) {
            binding.msvFilterLoanPackage.showDefaultLayout()
            spSector.addAll(data)

            if (defaultValue == getString(R.string.label_all_subsector)) {
                spSector.text = data.joinToString(", ") { it.key }
            } else {
                spSector.text = defaultValue
            }

            spSector.setOnCheckedChangeListener {
                cbSelectAll.isChecked = it
            }
        }
    }

    override fun initAction() {
        with(binding) {
            ivClose.setOnClickListener { dismiss() }

            cbSelectAll.setOnClickListener {
                spSector.isCheckedAll = cbSelectAll.isChecked
            }

            btnApply.setOnClickListener {
                if (spSector.isCheckedAll) {
                    callBack.invoke(datas)
                    dismiss()
                } else {
                    runCatching {
                        callBack.invoke(datas.filter {
                            spSector.text.contains(it.key)
                        })
                        dismiss()
                    }.onFailure {
                        it.printStackTrace()
                        callBack.invoke(emptyList())
                        dismiss()
                    }
                }
            }
        }
    }
}