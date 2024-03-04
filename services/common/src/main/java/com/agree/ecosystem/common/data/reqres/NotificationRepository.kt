package com.agree.ecosystem.common.data.reqres

import com.agree.ecosystem.common.data.reqres.model.notification.*
import com.agree.ecosystem.common.data.reqres.model.notification.detail.InboxDetailItem
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface NotificationRepository : DevRepository {
    fun fetchListNotifications(params: NotificationParams): Flowable<List<InboxItem>>
    fun fetchLoadMoreListsNotifications(params: NotificationParams): Flowable<List<InboxItem>>
    fun fetchSizeUnreadInbox(userId: String?): Flowable<InboxUnreadSizeItem>
    fun fetchDetailNotificatino(id: String?): Flowable<InboxDetailItem>
    fun updateStateInbox(id: String?, body: UpdateStateInboxPost): Flowable<UpdateInboxItem>
}
