package com.agree.ecosystem.common.presentation.base.activity

import com.agree.ecosystem.core.utils.utility.StatusBarColors

interface MenuDataContract : NotificationCounterDataContract {
    fun changeStatusBarColor(colors: StatusBarColors)
    fun doLogout()
    fun doForceLogout()
    fun onLogoutSuccess() {
        // Do Nothing
    }
}
