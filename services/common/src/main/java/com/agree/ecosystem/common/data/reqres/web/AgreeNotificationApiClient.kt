package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.common.data.reqres.model.notification.InboxItem
import com.agree.ecosystem.common.data.reqres.model.notification.InboxUnreadSizeItem
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateInboxItem
import com.agree.ecosystem.common.data.reqres.model.notification.UpdateStateInboxPost
import com.agree.ecosystem.common.data.reqres.model.notification.detail.InboxDetailItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.*

interface AgreeNotificationApiClient {
    @GET("utilities/v1/inbox")
    fun fetchListNotifications(
        @Query("quantity") quantity: Int,
        @Query("page") page: Int,
        @Query("user_id") userId: String?,
        @Query("type") type: String? = "company-member"
    ): Flowable<Response<DevApiResponse<List<InboxItem>>>>

    @GET("utilities/v1/inbox/count")
    fun fetchSizeUnreadNotification(
        @Query("user_id") userId: String?,
        @Query("type") type: String? = "company-member"
    ): Flowable<Response<DevApiResponse<InboxUnreadSizeItem>>>

    @GET("/utilities/v1/inbox/{id}")
    fun fetchDetailNotification(@Path("id") id: String): Flowable<Response<DevApiResponse<InboxDetailItem>>>

    @PUT("/utilities/v1/inbox/{id}")
    fun updateStateInbox(
        @Path("id") id: String?,
        @Body data: UpdateStateInboxPost
    ): Flowable<Response<DevApiResponse<UpdateInboxItem>>>
}
