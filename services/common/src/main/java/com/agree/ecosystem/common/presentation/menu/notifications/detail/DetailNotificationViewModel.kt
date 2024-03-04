package com.agree.ecosystem.common.presentation.menu.notifications.detail

import com.agree.ecosystem.common.domain.reqres.NotificationUseCase
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DetailInbox
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailNotificationViewModel(
    private val useCase: NotificationUseCase
) : DevViewModel() {

    private val _data = DevData<DetailInbox>().apply { default() }
    val data get() = _data

    fun fetchDetailDataNotification(inboxId: String) {
        useCase.fetchDetailNotification(inboxId)
            .setHandler(_data)
            .let { return@let disposable::add }
    }
}
