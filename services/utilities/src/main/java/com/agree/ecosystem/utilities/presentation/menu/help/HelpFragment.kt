package com.agree.ecosystem.utilities.presentation.menu.help

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.presentation.dialog.cs.CustomerServiceDialog
import com.agree.ecosystem.core.utils.utility.defaultErrorHandling
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.utilities.databinding.FragmentHelpBinding
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.agree.ecosystem.utilities.presentation.navigation.MainNavigation
import com.agree.ui.snackbar.errorSnackBar
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpFragment : AgrFragment<FragmentHelpBinding>(), HelpDataContract {

    private val adapter: HelpAdapter by lazy {
        HelpAdapter { help ->
            help?.let {
                nav.fromHelpToDetailHelp(it)
            }
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            rvHelpCategory.adapter = adapter
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnCallCs.setOnClickListener {
                CustomerServiceDialog().showNow(childFragmentManager, "dialog")
            }
            getData()
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(HelpObserver(this, viewModel))
    }

    override fun getData() {
        viewModel.getHelp {
            defaultErrorHandling(this, it.orEmpty(), ::getData)
        }
    }

    override fun onGetHelpLoading() {
        binding.msvHelpCategory.showLoadingLayout()
    }

    override fun onGetHelpSuccess(data: ArrayList<Help>) {
        binding.msvHelpCategory.showDefaultLayout()
        adapter.clear()
        adapter.addOrUpdateAll(data)
    }

    override fun onGetHelpFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    private val nav: MainNavigation by navigation { activity }
    private val viewModel: HelpViewModel by viewModel()
}
