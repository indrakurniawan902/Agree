package com.agree.ecosystem.utilities.presentation.menu.about

import android.view.View
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.FragmentAboutAgreeBinding
import com.agree.ui.snackbar.errorSnackBar
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutAgreeFragment : AgrFragment<FragmentAboutAgreeBinding>(), AboutAgreeDataContract {
    private val viewModel: AboutAgreeViewModel by viewModel()

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(AboutAgreeObserver(this, viewModel))
        viewModel.getAboutAgree()
    }

    override fun onGetAboutAgreeLoading() {
        with(binding) {
            msvAboutAgree.showLoadingLayout()
        }
    }

    override fun onGetAboutAgreeSuccess(data: String?) {
        data?.let {
            with(binding) {
                msvAboutAgree.showDefaultLayout()
                wvAboutAgree.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                wvAboutAgree.loadDataWithBaseURL(
                    null,
                    it.replace(
                        "{version_name}",
                        AppConfig.versionName
                    ),
                    "text/html; charset=utf-8", "UTF-8", null
                )
            }
        }
    }

    override fun onGetAboutAgreeFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }
}
