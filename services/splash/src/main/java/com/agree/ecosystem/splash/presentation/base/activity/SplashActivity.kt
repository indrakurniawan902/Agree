package com.agree.ecosystem.splash.presentation.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.splash.databinding.ActivitySplashBinding
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation

class SplashActivity :
    AgrActivity<ActivitySplashBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    lateinit var splashApi: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashApi = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashApi.setKeepOnScreenCondition { true }
    }

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    val resultLoginRegister =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                finish()
            }
        }
}
