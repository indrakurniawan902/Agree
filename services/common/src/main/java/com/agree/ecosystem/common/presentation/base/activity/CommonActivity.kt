package com.agree.ecosystem.common.presentation.base.activity

import android.Manifest
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.agree.ecosystem.auth.presentation.base.activity.AuthActivity
import com.agree.ecosystem.auth.presentation.navigation.navparams.screen.NavigationParams
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ActivityCommonBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.UnreadInbox
import com.agree.ecosystem.common.presentation.menu.home.bottomsheets.BottomSheetConnectionFragment
import com.agree.ecosystem.common.presentation.menu.notifications.NotificationViewModel
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.StatusBarColors
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navHost
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.startActivity
import com.oratakashi.viewbinding.core.tools.visible
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommonActivity :
    AgrActivity<ActivityCommonBinding>(),
    MenuDataContract,
    LocaleActivityDelegate by LocaleActivityDelegation() {

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {
        super.initUI()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                eventBus.events.collectLatest {
                    when (it) {
                        is AppEvent.LOGOUT -> doLogout()
                        is AppEvent.FORCE_LOGOUT -> doForceLogout()
                        is AppEvent.FETCH_NOTIFICATION_BADGE -> getNotificationBadge(prefs.userId)
                        else -> return@collectLatest
                    }
                }
            }
        }
        with(binding) {
            val nav = navHost(R.id.nav_host_fragment_menu)
            nav.navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment,
                    R.id.partnershipListFragment,
                    R.id.notificationsFragment,
                    R.id.settingsFragment,
                    R.id.monitoringFragment -> {
                        showBottomNav()
                    }

                    else -> hideBottomNav()
                }
                if (destination.id == R.id.homeFragment) {
                    changeStatusBarColor(StatusBarColors.PRIMARY10)
                } else {
                    changeStatusBarColor(StatusBarColors.WHITE)
                }
            }

            bnMain.setupWithNavController(nav.navController)
            bnMain.setOnItemSelectedListener {
                NavigationUI.onNavDestinationSelected(it, nav.navController)
                nav.navController.popBackStack(it.itemId, false)
            }
        }
        addBadge()
    }

    override fun initAction() {
        super.initAction()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            initNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initNotificationPermission() {
        if (hasPermission(Manifest.permission.POST_NOTIFICATIONS)) {
            return
        }

        requestPermissionsSafely(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(MenuObserver(this, notifViewModel))
        getNotificationBadge(prefs.userId)

        /**
         * Observe connection and show connection bottom sheet only if user is in home fragment
         */
        val navController = navHost(R.id.nav_host_fragment_menu).navController
        connection.observe().onEach {
            if (
                it != ConnectivityObserver.Status.Available &&
                navController.currentDestination?.id == R.id.homeFragment
            ) {
                BottomSheetConnectionFragment().show(supportFragmentManager, "connection")
            }
        }.launchIn(lifecycleScope)
    }

    private fun showBottomNav(duration: Int = 400) {
        with(binding) {
            if (bnMain.visibility == View.VISIBLE) return
            bnMain.visibility = View.VISIBLE
            val animate = TranslateAnimation(0f, 0f, bnMain.height.toFloat(), 0f)
            animate.duration = duration.toLong()
            bnMain.startAnimation(animate)
        }
    }

    private fun hideBottomNav(duration: Int = 400) {
        with(binding) {
            if (bnMain.visibility == View.GONE) return
            val animate = TranslateAnimation(0f, 0f, 0f, bnMain.height.toFloat())
            animate.duration = duration.toLong()
            bnMain.startAnimation(animate)
            bnMain.visibility = View.GONE
        }
    }

    private fun addBadge() {
        with(binding) {
            val mBottomNavigationMenuView = bnMain.getChildAt(0) as BottomNavigationMenuView
            val menu = mBottomNavigationMenuView.getChildAt(3)
            val itemMenu = menu as BottomNavigationItemView

            viewBadge = LayoutInflater.from(this@CommonActivity).inflate(
                R.layout.layout_custom_badge,
                itemMenu, true
            )
            tvBadge = viewBadge.findViewById(R.id.tvBadge)
            tvBadge.gone()
        }
    }

    override fun changeStatusBarColor(colors: StatusBarColors) {
        val value = TypedValue()
        theme.resolveAttribute(colors.value, value, true)
        window.statusBarColor = value.data
    }

    override fun doLogout() {
        startActivity(AuthActivity::class.java) {
            it.putExtra("screen", NavigationParams.LOGOUT.name)
        }
        finish()
    }

    override fun doForceLogout() {
        startActivity(AuthActivity::class.java) {
            it.putExtra("screen", NavigationParams.FORCE_LOGOUT.name)
        }
        finish()
    }

    override fun getNotificationBadge(userId: String?) {
        notifViewModel.fetchSizeUnreadNotification(prefs.userId)
    }

    override fun onNotificationBadgeSuccess(data: UnreadInbox) {
        if (data.totalUnreadInbox > 0) {
            tvBadge.visible()
            if (data.totalUnreadInbox > 99) {
                tvBadge.text = "99+"
            } else {
                tvBadge.text = data.totalUnreadInbox.toString()
            }
        } else {
            tvBadge.gone()
        }
    }

    private val prefs: AgrPrefUsecase by inject()

    private val notifViewModel: NotificationViewModel by viewModel()
    private val connection: ConnectivityObserver by inject()
    private lateinit var tvBadge: TextView
    private lateinit var viewBadge: View
}
