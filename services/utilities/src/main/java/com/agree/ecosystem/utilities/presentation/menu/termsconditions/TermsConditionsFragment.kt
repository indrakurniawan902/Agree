package com.agree.ecosystem.utilities.presentation.menu.termsconditions

import android.view.View
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.FragmentTermsConditionsBinding
import com.agree.ui.snackbar.errorSnackBar
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsConditionsFragment :
    AgrFragment<FragmentTermsConditionsBinding>(),
    TermsConditionsDataContract {

    private val viewModel: TermsConditionsViewModel by viewModel()

    override fun initAction() {
        super.initAction()
        binding.toolbar.setBackButtonOnClick {
            requireActivity().onBackPressed()
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(TermsConditionsObserver(this, viewModel))
        viewModel.getTermsConditions()
    }

    override fun onGetTermsConditionsLoading() {
        with(binding) {
            msvTermsConditions.showLoadingLayout()
        }
    }

    override fun onGetTermsConditionsSuccess(data: String?) {
        data?.let {
            with(binding) {
                msvTermsConditions.showDefaultLayout()
                wvTermsConditions.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                wvTermsConditions.loadDataWithBaseURL(
                    null,
                    it,
                    "text/html; charset=utf-8",
                    "UTF-8",
                    null
                )
            }
        }
    }

    override fun onGetTermsConditionsFailed(e: Throwable?) {
        requireActivity()
        errorSnackBar(e?.message.toString())
    }
}
