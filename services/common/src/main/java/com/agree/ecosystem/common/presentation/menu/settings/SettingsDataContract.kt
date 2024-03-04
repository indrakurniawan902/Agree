package com.agree.ecosystem.common.presentation.menu.settings

import com.agree.ecosystem.users.domain.reqres.model.profile.Profile

interface SettingsDataContract {
    fun onGetProfileLoading()

    fun onGetProfileSuccess(data: Profile)

    fun onGetProfileFailed(e: Throwable?)
}
