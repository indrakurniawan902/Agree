package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.data.reqres.model.notification.NotificationParams
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateStateInboxPost
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.common.domain.reqres.model.notification.UnreadInbox
import com.agree.ecosystem.common.domain.reqres.model.notification.UpdateInbox
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DetailInbox
import io.reactivex.Flowable

interface NotificationUseCase {
    fun fetchListNotifications(params: NotificationParams): Flowable<List<Inbox>>
    fun fetchLoadMoreListsNotifications(params: NotificationParams): Flowable<List<Inbox>>
    fun fetchSizeListNotifications(userId: String): Flowable<UnreadInbox>
    fun fetchDetailNotification(id: String): Flowable<DetailInbox>
    fun updateInbox(id: String, body: UpdateStateInboxPost): Flowable<UpdateInbox>
}
