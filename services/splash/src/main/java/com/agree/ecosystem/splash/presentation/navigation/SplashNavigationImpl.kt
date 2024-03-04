package com.agree.ecosystem.splash.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.auth.presentation.base.activity.AuthActivity
import com.agree.ecosystem.common.presentation.base.activity.CommonActivity
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.splash.presentation.base.activity.SplashActivity
import com.oratakashi.viewbinding.core.tools.startActivity

class SplashNavigationImpl(
    private val activity: Activity?,
    private val nav: NavController?,
    private val prefs: AgrPrefUsecase
) : SplashNavigation {
    override fun toUpdater() {
//        nav?.navigate(
//            SplashFragmentDirections.actionSplashFragmentToUpdaterFragment()
//        )
    }

    override fun toLoginOrMenu() {
        val splashActivity = activity as SplashActivity
        splashActivity.splashApi.setKeepOnScreenCondition { false }
        if (prefs.accessToken.isNotEmpty()) {
            runCatching {
                activity?.startActivity(CommonActivity::class.java)
                activity?.finish()
            }.onFailure {
                it.printStackTrace()
            }
        } else {
            runCatching {
                activity?.startActivity(AuthActivity::class.java)
                activity?.finish()
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

//    override fun fromOnboardToLogin() {
//        runCatching {
//            val slashActivity = activity as SplashActivity?
//            slashActivity?.resultLoginRegister?.launch(
//                Intent(
//                    activity, AuthActivity::class.java
//                ).apply {
//                    putExtra("screen", NavigationParams.LOGIN.name)
//                }
//            )
//        }.onFailure {
//            it.printStackTrace()
//        }
//    }
//
//    override fun fromOnboardToRegister() {
//        runCatching {
//            val slashActivity = activity as SplashActivity?
//            slashActivity?.resultLoginRegister?.launch(
//                Intent(
//                    activity, AuthActivity::class.java
//                ).apply {
//                    putExtra("screen", NavigationParams.REGISTER.name)
//                    putExtra("fromScreen", RegisterParams.FROM_SPLASH.name)
//                }
//            )
//        }.onFailure {
//            it.printStackTrace()
//        }
//    }
}
