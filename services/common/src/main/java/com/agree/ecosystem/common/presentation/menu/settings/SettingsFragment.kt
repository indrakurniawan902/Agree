package com.agree.ecosystem.common.presentation.menu.settings

import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentSettingsBinding
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.agree.ui.snackbar.errorSnackBar
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : AgrFragment<FragmentSettingsBinding>(), SettingsDataContract {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.navController = menuNav.getNavController()
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(SettingsObserver(this, viewModel))
        viewModel.getProfile(prefs.userId)
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            cvLogout.setOnClickListener {
                DialogUtils.showCustomDialog(
                    context = requireContext(),
                    title = "",
                    message = getString(R.string.label_logout_dialog),
                    positiveAction = Pair(getString(R.string.action_cancel), null),
                    negativeAction = Pair(getString(R.string.action_yes)) { logout() },
                    autoDismiss = false
                )
            }
            tvOfflineMonitoring.setOnClickListener {
                menuNav.fromSettingsToOfflineMonitoring()
            }
            tvChangePassword.setOnClickListener {
                menuNav.fromMenuToChangePassword()
            }
            tvSeeProfile.setOnClickListener {
                menuNav.fromSettingsToProfile()
            }
            tvTermsAndConditions.setOnClickListener {
                menuNav.fromSettingsToTermsConditions()
            }
            tvAboutAgree.setOnClickListener {
                menuNav.fromSettingsToAboutAgree()
            }
            tvHelpCenter.setOnClickListener {
                menuNav.fromSettingsToHelp()
            }
        }
    }

    private fun logout() {
        broadcastEvent { AppEvent.LOGOUT }
    }

    override fun onGetProfileLoading() {
        with(binding) {
            msvProfile.showLoadingLayout()
        }
    }

    override fun onGetProfileSuccess(data: Profile) {
        with(binding) {
            msvProfile.showDefaultLayout()
            tvProfileName.text = data.name
            imgProfile.url = data.avatar
        }
    }

    override fun onGetProfileFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    private val menuNav: MenuNavigation by navigation { activity }
    private val prefs: AgrPrefUsecase by inject()
    private val viewModel: SettingsViewModel by viewModel()
}
