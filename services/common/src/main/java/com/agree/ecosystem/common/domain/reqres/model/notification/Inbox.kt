package com.agree.ecosystem.common.domain.reqres.model.notification

import androidx.annotation.Keep

@Keep
data class Inbox(
    val category: String,
    val companyId: String,
    val companyMemberId: String,
    val companySubMemberId: String,
    val createdAt: String,
    val createdBy: String,
    val data: String,
    val description: String,
    val fieldAssistantId: String,
    val id: String,
    val isDeleted: Boolean,
    val isRead: Boolean,
    val modifiedAt: String,
    val modifiedBy: String,
    val status: String,
    val subVesselId: String,
    val title: String,
    val type: String,
    val userId: String
)
