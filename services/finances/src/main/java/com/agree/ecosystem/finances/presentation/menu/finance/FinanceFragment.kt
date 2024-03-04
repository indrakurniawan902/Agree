package com.agree.ecosystem.finances.presentation.menu.finance

import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentFinanceBinding
import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.CultivatorFragment
import com.agree.ecosystem.finances.presentation.menu.finance.loanactive.ListLoanActiveFragment
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan.ListLoanPackageFragment
import com.devbase.utils.util.getDrawableResource
import com.google.android.material.tabs.TabLayoutMediator

class FinanceFragment : AgrFragment<FragmentFinanceBinding>() {

    private val args: FinanceFragmentArgs by navArgs()

    private var mediator: TabLayoutMediator? = null
    override fun onResume() {
        super.onResume()
        initUI()
    }

    override fun initUI() {
        super.initUI()
        setupViewPager()
    }

    private fun createViewPagerAdapter(): FragmentStateAdapter {
        return object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int) =
                when (position) {
                    0 -> ListLoanPackageFragment()
                    1 -> CultivatorFragment()
                    else -> ListLoanActiveFragment()
                }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    private fun setupViewPager() {
        binding.vpFinanceMenu.apply {
            adapter = createViewPagerAdapter()
            mediator = TabLayoutMediator(binding.tabFinanceMenu, this) { tab, position ->
                val tabLable = when (position) {
                    0 -> getString(R.string.label_tab_Loan_Package)
                    1 -> getString(R.string.label_tab_cultivator_data)
                    else -> getString(R.string.label_tab_active_loan)
                }
                val iconLable = when (position) {
                    0 -> getDrawableResource(R.drawable.ic_loan_package)
                    1 -> getDrawableResource(R.drawable.ic_shield)
                    else -> getDrawableResource(R.drawable.ic_loan_active)
                }
                tab.text = tabLable
                tab.icon = iconLable
            }
        }.currentItem = args.initTab
        mediator?.attach()
    }

    private fun cleanMeditor() {
        mediator?.detach()
        mediator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanMeditor()
    }
}
