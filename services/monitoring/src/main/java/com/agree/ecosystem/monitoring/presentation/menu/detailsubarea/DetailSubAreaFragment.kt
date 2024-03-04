package com.agree.ecosystem.monitoring.presentation.menu.detailsubarea

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.monitoring.databinding.FragmentDetailSubAreaBinding
import com.agree.ui.UiKitDrawable

class DetailSubAreaFragment : AgrFragment<FragmentDetailSubAreaBinding>() {

    override fun initAction() {
        super.initAction()
        with(binding) {
            ibShow.setOnClickListener {
                if (!expCommodity.isExpanded) {
                    ibShow.setImageDrawable(requireActivity().getDrawable(UiKitDrawable.ic_chevron_up))
                    expCommodity.isExpanded = true
                } else {
                    ibShow.setImageDrawable(requireActivity().getDrawable(UiKitDrawable.ic_chevron_down))
                    expCommodity.isExpanded = false
                }
            }
        }
    }
}
