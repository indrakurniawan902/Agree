package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.data.reqres.NotificationRepository
import com.agree.ecosystem.common.data.reqres.model.notification.NotificationParams
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateStateInboxPost
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.common.domain.reqres.model.notification.UnreadInbox
import com.agree.ecosystem.common.domain.reqres.model.notification.UpdateInbox
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DetailInbox
import io.reactivex.Flowable

class NotificationInteractor(
    private val repo: NotificationRepository
) : NotificationUseCase {
    override fun fetchListNotifications(params: NotificationParams): Flowable<List<Inbox>> {
        return repo.fetchLoadMoreListsNotifications(params).map {
            val data = arrayListOf<Inbox>()
            it.sortedByDescending { it.createdAt }.map { value ->
                data.add(value.toInbox())
            }
            return@map data
        }
    }

    override fun fetchLoadMoreListsNotifications(params: NotificationParams): Flowable<List<Inbox>> {
        return repo.fetchLoadMoreListsNotifications(params).map {
            val data = arrayListOf<Inbox>()
            it.sortedByDescending { it.createdAt }.map { value ->
                data.add(value.toInbox())
            }
            return@map data
        }
    }

    override fun fetchSizeListNotifications(userId: String): Flowable<UnreadInbox> {
        return repo.fetchSizeUnreadInbox(userId).map {
            return@map it.toUnreadInbox()
        }
    }

    override fun fetchDetailNotification(id: String): Flowable<DetailInbox> {
        return repo.fetchDetailNotificatino(id).map {
            return@map it.toDetailInbox()
        }
    }

    override fun updateInbox(id: String, body: UpdateStateInboxPost): Flowable<UpdateInbox> {
        return repo.updateStateInbox(id, body).map {
            return@map it.toUpdateInbox()
        }
    }
}
