package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.common.data.reqres.model.notification.InboxItem
import com.agree.ecosystem.common.data.reqres.model.notification.InboxUnreadSizeItem
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateInboxItem
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateStateInboxPost
import com.agree.ecosystem.common.data.reqres.model.notification.detail.InboxDetailItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response

class AgreeNotificationApi(
    private val api: AgreeNotificationApiClient
) : AgreeNotificationApiClient, WebService {

    override fun fetchListNotifications(
        quantity: Int,
        page: Int,
        userId: String?,
        type: String?
    ): Flowable<Response<DevApiResponse<List<InboxItem>>>> {
        return api.fetchListNotifications(quantity, page, userId, "company-member")
    }

    override fun fetchSizeUnreadNotification(
        userId: String?,
        type: String?
    ): Flowable<Response<DevApiResponse<InboxUnreadSizeItem>>> {
        return api.fetchSizeUnreadNotification(userId, "company-member")
    }

    override fun fetchDetailNotification(id: String): Flowable<Response<DevApiResponse<InboxDetailItem>>> {
        return api.fetchDetailNotification(id)
    }

    override fun updateStateInbox(
        id: String?,
        data: UpdateStateInboxPost
    ): Flowable<Response<DevApiResponse<UpdateInboxItem>>> {
        return api.updateStateInbox(id, data)
    }
}
