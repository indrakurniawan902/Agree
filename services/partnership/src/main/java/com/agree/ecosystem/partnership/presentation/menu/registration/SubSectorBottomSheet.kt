package com.agree.ecosystem.partnership.presentation.menu.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.LayoutCommodityBottomSheetBinding
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding

class SubSectorBottomSheet(private val data: List<Commodity>, private val subSector: SubSector, private val onDataChecked: (data: String) -> Unit) :
    BottomSheetDialogFragment() {

    private val binding: LayoutCommodityBottomSheetBinding by viewBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initAction()
    }

    private fun initUI() {
        with(binding) {
            tvTitle.text = getString(R.string.label_choose_agricultural, subSector.name)
        }
    }

    private fun initData() {
        with(binding) {
            val dataCommodities = arrayListOf<String>()
            data.map {
                dataCommodities.add(it.name)
            }
            cbContainer.addAll(dataCommodities)
            val subSectorData = arrayListOf<String>()
            subSector.commodities.map {
                subSectorData.add(it.name)
            }
            cbContainer.text = subSectorData.joinToString(",")
        }
    }

    private fun initAction() {
        with(binding) {
            btnChooseCommodity.setOnClickListener {
                onDataChecked(cbContainer.text)
                dismiss()
            }
            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "CommodityBottomSheet"
    }
}
