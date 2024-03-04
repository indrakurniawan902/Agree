package com.agree.ecosystem.splash.presentation.menu.updater

import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo

interface UpdaterContract {

    fun onAppInfoIdle() {
        // Do Nothing
    }

    fun onAppInfoLoading() {
        // Do Nothing
    }

    fun onAppInfoSuccess(data: ArrayList<AppInfo>) {
        // Do Nothing
    }

    fun onAppInfoFailed(e: Throwable?) {
        // Do Nothing
    }
}
