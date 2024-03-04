package com.agree.ecosystem.splash.presentation.menu.updater

import com.agree.ecosystem.auth.databinding.FragmentEmptyDialogBinding
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.splash.databinding.FragmentUpdaterBinding
import com.agree.ecosystem.splash.presentation.base.activity.SplashActivity
import com.agree.ecosystem.splash.presentation.navigation.SplashNavigation
import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo
import com.oratakashi.viewbinding.core.tools.finish
import com.telkom.legion.component.button.fill.LgnPrimaryFitButton
import com.telkom.legion.component.button.outline.LgnErrorOutlineFitButton
import com.telkom.legion.extension.bottomsheet.LgnBottomSheet
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdaterFragment : AgrFragment<FragmentUpdaterBinding>(), UpdaterContract {

    override fun initAction() {
        super.initAction()
        viewModel.getAppInfo()
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(UpdaterObserver(this, viewModel))
    }

    override fun onAppInfoSuccess(data: ArrayList<AppInfo>) {
        super.onAppInfoSuccess(data)
        prefs.contactInfo = data[0].contact
        nav.toLoginOrMenu()
    }

    override fun onAppInfoFailed(e: Throwable?) {
        super.onAppInfoFailed(e)
        if (AppConfig.isDebug) {
            val splashActivity = activity as SplashActivity
            splashActivity.splashApi.setKeepOnScreenCondition { false }
            LgnBottomSheet.setup(requireContext()) {
                header = LgnBottomSheet.HeaderView.DefaultWithHandler(
                    title = "Terjadi Error di NocoDB !",
                    description = "Terdapat masalah pada NocoDB maupun API lainnya, aplikasi mungkin tidak berjalan secara maksimal!\n\nError Message: ${e?.message ?: "Unknown"}",
                    showClose = false
                )

                setContent<FragmentEmptyDialogBinding> {
                    // Do Nothing
                }

                addButton(LgnErrorOutlineFitButton(requireContext())) {
                    text = "Keluar"
                    setOnClickListener {
                        finish()
                        dismiss()
                    }
                }

                addButton(LgnPrimaryFitButton(requireContext())) {
                    text = "Lanjut"
                    setOnClickListener {
                        nav.toLoginOrMenu()
                        dismiss()
                    }
                }
            }.show(childFragmentManager, "error")
        } else {
            nav.toLoginOrMenu()
        }
    }

    private val nav by navigation<SplashNavigation> { activity }
    private val viewModel: UpdaterViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
}
