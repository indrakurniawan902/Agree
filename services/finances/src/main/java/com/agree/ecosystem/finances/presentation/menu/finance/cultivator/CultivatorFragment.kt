package com.agree.ecosystem.finances.presentation.menu.finance.cultivator

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.databinding.FragmentCultivatorBinding
import com.agree.ecosystem.finances.presentation.navigation.DataCultivatorNavigation

class CultivatorFragment : AgrFragment<FragmentCultivatorBinding>() {
    override fun initAction() {
        super.initAction()
    }
    override fun initUI() {
        super.initUI()
        with(binding) {
            mvMemberList.setOnClickListener {
                menuNav.fromTabCultivatorDataToCultivatorDataActivity("")//7a3d7ff6-476d-43e5-a62f-35f2f6a38cef")
            }
        }
    }

    private val menuNav: DataCultivatorNavigation by navigation { activity }
}
