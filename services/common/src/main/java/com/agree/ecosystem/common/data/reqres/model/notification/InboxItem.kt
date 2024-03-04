package com.agree.ecosystem.common.data.reqres.model.notification

import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.google.gson.annotations.SerializedName

data class InboxItem(
    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("company_id")
    val companyId: String? = null,

    @field:SerializedName("company_member_id")
    val companyMemberId: String? = null,

    @field:SerializedName("company_submember_id")
    val companySubmemberId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("created_by")
    val createdBy: String? = null,

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("field_assistant_id")
    val fieldAssistantId: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("is_deleted")
    val isDeleted: Boolean? = false,

    @field:SerializedName("is_read")
    val isRead: Boolean? = false,

    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,

    @field:SerializedName("modified_by")
    val modifiedBy: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("subvessel_id")
    val subvesselId: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null
) {
    fun toInbox(): Inbox {
        return Inbox(
            category = category.orEmpty(),
            companyId = companyId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companySubMemberId = companySubmemberId.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            data = data.orEmpty(),
            description = description.orEmpty(),
            fieldAssistantId = fieldAssistantId.orEmpty(),
            id = id.orEmpty(),
            isDeleted = isDeleted ?: false,
            isRead = isRead ?: false,
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            status = status.orEmpty(),
            subVesselId = subvesselId.orEmpty(),
            title = title.orEmpty(),
            type = type.orEmpty(),
            userId = userId.orEmpty()
        )
    }
}
