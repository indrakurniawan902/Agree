package com.agree.ecosystem.auth.presentation.base.activity

import com.agree.ecosystem.core.utils.utility.StatusBarColors

interface AuthDataContract {
    fun changeStatusBarColor(colors: StatusBarColors)
    fun onLogoutSuccess() {
        // Do Nothing
    }
}
