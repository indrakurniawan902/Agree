package com.agree.ecosystem.auth.presentation.base.activity

import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.auth.R
import com.agree.ecosystem.auth.databinding.ActivityAuthBinding
import com.agree.ecosystem.auth.presentation.menu.changepassword.ChangePasswordFragmentArgs
import com.agree.ecosystem.auth.presentation.menu.verification.VerificationFragmentArgs
import com.agree.ecosystem.auth.presentation.navigation.navparams.changepassword.FromParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.screen.NavigationParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.InputTypeParams
import com.agree.ecosystem.auth.presentation.navigation.navparams.verification.VerificationTypeParams
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.StatusBarColors
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.ecosystem.core.utils.utility.navigation.navHost
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import com.oratakashi.viewbinding.core.tools.startActivity
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity :
    AgrActivity<ActivityAuthBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation(),
    AuthDataContract {
    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(AuthObserver(this, viewModel))
    }

    override fun initUI() {
        super.initUI()
        lifecycleScope.launchWhenResumed {
            eventBus.events.collectLatest {
                when (it) {
                    AppEvent.FORCE_LOGOUT -> {
                        finish()
                    }
                    else -> return@collectLatest
                }
            }
        }

        val nav = navHost(R.id.nav_host_auth)
        nav.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                changeStatusBarColor(StatusBarColors.PRIMARY10)
            } else {
                changeStatusBarColor(StatusBarColors.WHITE)
            }
        }

        when (intent.getStringExtra("screen")) {
            NavigationParams.LOGIN.name -> {
                initGraph(
                    R.id.nav_host_auth,
                    R.navigation.nav_auth
                ) {
                    it.setStartDestination(R.id.loginFragment)
                }
            }
            NavigationParams.FORGOT_ACCOUNT.name -> {
                initGraph(
                    R.id.nav_host_auth,
                    R.navigation.nav_auth
                ) {
                    it.setStartDestination(R.id.forgotAccountFragment)
                }
            }
            NavigationParams.VERIFICATION.name -> {
                val inputValue = intent.getStringExtra("inputValue") ?: String()
                val inputType = intent.getStringExtra("inputType") ?: String()
                val verificationType = intent.getStringExtra("verificationType") ?: String()

                val enumInputType = enumValueOf<InputTypeParams>(inputType)
                val enumVerificationTypeData = enumValueOf<VerificationTypeParams>(verificationType)

                initGraph(
                    R.id.nav_host_auth,
                    R.navigation.nav_auth,
                    VerificationFragmentArgs(
                        inputValue = inputValue,
                        inputType = enumInputType,
                        verificationType = enumVerificationTypeData
                    ).toBundle()
                ) {
                    it.setStartDestination(R.id.verificationFragment)
                }
            }
            NavigationParams.CHANGE_PASSWORD.name -> {
                val from = intent.getStringExtra("from").orEmpty()
                val enumFrom = enumValueOf<FromParams>(from)
                initGraph(
                    R.id.nav_host_auth,
                    R.navigation.nav_auth,
                    ChangePasswordFragmentArgs(
                        from = enumFrom
                    ).toBundle()
                ) {
                    it.setStartDestination(R.id.changePasswordFragment)
                }
            }
            NavigationParams.LOGOUT.name -> {
                viewModel.trackLogout(prefs.userName)
                viewModel.logout()
                initGraph(
                    R.id.nav_host_auth,
                    R.navigation.nav_auth
                ) {
                    it.setStartDestination(R.id.logoutFragment)
                }
            }
            NavigationParams.FORCE_LOGOUT.name -> {
                viewModel.trackLogout(prefs.userName)
                viewModel.logout()
                initGraph(
                    R.id.nav_host_auth,
                    R.navigation.nav_auth
                ) {
                    it.setStartDestination(R.id.loginFragment)
                }
            }
        }
    }

    override fun changeStatusBarColor(colors: StatusBarColors) {
        val value = TypedValue()
        theme.resolveAttribute(colors.value, value, true)
        window.statusBarColor = value.data
    }

    override fun onLogoutSuccess() {
        super.onLogoutSuccess()
        prefs.clear()
        when (intent.getStringExtra("screen")) {
            NavigationParams.LOGOUT.name -> {
                val strActivity =
                    "com.agree.ecosystem.splash.presentation.base.activity.SplashActivity"
                startActivity(Class.forName(strActivity))
                finish()
            }
        }
    }

    private val viewModel: AuthViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
}
