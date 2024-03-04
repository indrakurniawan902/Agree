package com.agree.ecosystem.agreepedia.presentation.menu.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.agreepedia.R
import com.agree.ecosystem.agreepedia.databinding.FragmentSectorDialogBinding
import com.agree.ecosystem.agreepedia.presentation.menu.home.AgreepediaViewModel
import com.agree.ecosystem.agreepedia.utility.enums.FilterSector
import com.agree.ecosystem.agreepedia.utility.enums.FilterSort
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ui.UiKitStyle
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.telkom.legion.component.radio.base.LgnRadioButton
import kotlinx.coroutines.launch

class SectorDialogFragment(
    private val viewModel: AgreepediaViewModel,
    private val callback: () -> Unit
) : AgrBottomSheet(), SectorDialogDataContract {

    private val binding: FragmentSectorDialogBinding by viewBinding()

    override fun initData() {
        super.initData()
        with(binding) {
            rgSector.addAll(
                data = listOf(
                    getString(R.string.label_see_all),
                    getString(R.string.label_agriculture),
                    getString(R.string.label_fishery),
                    getString(R.string.label_livestock),
                ),
                "",
                style = UiKitStyle.BaseRadioButtonPrimary
            )
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(SectorDialogObserver(this, viewModel))
    }

    override fun initAction() {
        with(binding) {
            ivClose.setOnClickListener { dismiss() }
            btnApply.setOnClickListener {
                lifecycleScope.launch {
                    val sortValue = rgSort.radioGroup
                        .children
                        .filterIsInstance(LgnRadioButton::class.java)
                        .first { it.isChecked }
                        .tag
                        .toString()
                        .toFilterSort()

                    val sectorValue = rgSector.text.toFilterSector()

                    viewModel.filterSort.emit(sortValue ?: FilterSort.LATEST)
                    viewModel.filterSector.emit(sectorValue ?: FilterSector.SHOWALL)
                    callback.invoke()
                    dismiss()
                }
            }
        }
    }

    override fun onSortChange(sort: FilterSort) {
        binding.rgSort.radioGroup
            .children
            .filterIsInstance(LgnRadioButton::class.java)
            .first { it.tag == sort.value }
            .isChecked = true
    }

    override fun onSectorChange(sector: FilterSector) {
        binding.rgSector.text = sector.value.ifEmpty {
            getString(R.string.label_see_all)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }
}
