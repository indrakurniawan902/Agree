package com.agree.ecosystem.core.utils.utility.eventbus

sealed class AppEvent {
    object LOGOUT : AppEvent()
    object FORCE_LOGOUT : AppEvent()

    object CHANGE_STATUS_BAR_WHITE : AppEvent()
    object CHANGE_STATUS_BAR_PRIMARY10 : AppEvent()

    object CHANGE_STATUS_BAR_PRIMARY500 : AppEvent()

    object REGISTRATION_PARTNERSHIP_COMPLETE : AppEvent()

    object FETCH_NOTIFICATION_BADGE : AppEvent()
}
