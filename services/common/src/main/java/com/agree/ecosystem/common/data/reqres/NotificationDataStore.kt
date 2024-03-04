package com.agree.ecosystem.common.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.common.data.reqres.model.notification.*
import com.agree.ecosystem.common.data.reqres.model.notification.detail.InboxDetailItem
import com.agree.ecosystem.common.data.reqres.web.AgreeNotificationApi
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import io.reactivex.Flowable

@WorkerThread
class NotificationDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreeNotificationApi
) : NotificationRepository {

    override fun fetchListNotifications(params: NotificationParams): Flowable<List<InboxItem>> {
        return webService.fetchListNotifications(
            params.quantity,
            params.page,
            params.idUser
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun fetchLoadMoreListsNotifications(params: NotificationParams): Flowable<List<InboxItem>> {
        return webService.fetchListNotifications(
            params.quantity,
            params.page,
            params.idUser
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun fetchSizeUnreadInbox(userId: String?): Flowable<InboxUnreadSizeItem> {
        return webService.fetchSizeUnreadNotification(userId).lift(flowableApiError())
            .map { it.data }
    }

    override fun fetchDetailNotificatino(id: String?): Flowable<InboxDetailItem> {
        return webService.fetchDetailNotification(id!!).lift(flowableApiError())
            .map { it.data }
    }

    override fun updateStateInbox(
        id: String?,
        body: UpdateStateInboxPost
    ): Flowable<UpdateInboxItem> {
        return webService.updateStateInbox(id, body)
            .lift(flowableApiError())
            .map { it.data }
    }
}
