package com.agree.ecosystem.common.data.reqres.model.notification.detail

import com.agree.ecosystem.common.domain.reqres.model.notification.detail.*
import com.google.gson.annotations.SerializedName

data class InboxDetailItem(
    @field:SerializedName("id")
    val id: String? = "",

    @field:SerializedName("company_id")
    val companyId: String? = "",

    @field:SerializedName("company_member_id")
    val companyMemberId: String? = "",

    @field:SerializedName("worker_id")
    val workerId: String? = "",

    @field:SerializedName("subvessel_id")
    val subVesselId: String? = "",

    @field:SerializedName("submission_id")
    val submissionId: String? = "",

    @field:SerializedName("user_id")
    val userId: String? = "",

    @field:SerializedName("type")
    val type: String? = "",

    @field:SerializedName("title")
    val title: String? = "",

    @field:SerializedName("category")
    val category: String? = "",

    @field:SerializedName("description")
    val description: String? = "",

    @field:SerializedName("created_at")
    val createdAt: String? = "",

    @field:SerializedName("created_by")
    val createdBy: String? = "",

    @field:SerializedName("is_read")
    val isRead: Boolean?,

    @field:SerializedName("status")
    val status: String? = "",

    @field:SerializedName("data")
    val data: DataDetailInboxItem? = null
) {
    fun toDetailInbox() = DetailInbox(
        id = id.orEmpty(),
        companyId = companyId.orEmpty(),
        companyMemberid = companyMemberId.orEmpty(),
        workerId = workerId.orEmpty(),
        subVesselId = subVesselId.orEmpty(),
        submissionId = submissionId.orEmpty(),
        userId = userId.orEmpty(),
        type = type.orEmpty(),
        title = title.orEmpty(),
        category = category.orEmpty(),
        description = description.orEmpty(),
        createdAt = createdAt.orEmpty(),
        createdBy = createdBy.orEmpty(),
        isRead = isRead ?: false,
        status = status.orEmpty(),
        data = data?.toDataDetailInbox() ?: DataDetailInbox(
            "",
            "",
            "",
            listOf(),
            listOf(),
            DataRejectedItem("", "").toDataRejected()
        )
    )
}

data class DataDetailInboxItem(
    @field:SerializedName("description")
    val descriptionDataDetailInbox: String? = "",

    @field:SerializedName("status")
    val statusDataDetailInbox: String? = "",

    @field:SerializedName("title")
    val titleDataDetailInbox: String? = "",

    @field:SerializedName("vessels")
    val vessels: List<VesselsLastResultRegistrationDataItem>? = mutableListOf(),

    @field:SerializedName("subsectors")
    val subsectorsDataDetailInbox: List<DataSubSectorDetailNotificationItem>? = mutableListOf(),

    @field:SerializedName("data")
    val dataRejected: DataRejectedItem? = null
) {
    fun toDataDetailInbox() = DataDetailInbox(
        this.descriptionDataDetailInbox.orEmpty(),
        this.statusDataDetailInbox.orEmpty(),
        this.titleDataDetailInbox.orEmpty(),
        this.vessels?.map { it.toVesselsLastResultRegistration() }.orEmpty(),
        this.subsectorsDataDetailInbox?.map { it.toDataSubSectorsDetailNotification() }.orEmpty(),
        this.dataRejected?.toDataRejected() ?: DataRejected("", "")
    )
}

data class DataSubSectorDetailNotificationItem(
    @field:SerializedName("id")
    val idDataSubSector: String? = null,

    @field:SerializedName("name")
    val nameDataSubSector: String? = null,

    @field:SerializedName("sector_name")
    val sectorNameDataSubSector: String? = null,

    @field:SerializedName("status")
    val statusDataSubSector: String? = null,

    @field:SerializedName("field_assistant_name")
    val fieldAssistantNameDataSubSector: String? = null,

    @field:SerializedName("commodities")
    val commoditiesDataSubSector: List<CommoditiesDetailNotificationItem>? = null,

    @field:SerializedName("data")
    val data: DataRejectedItem? = null
) {
    fun toDataSubSectorsDetailNotification() =
        DataSubSectorsDetailNotification(
            idDataSubSector = this.idDataSubSector ?: "",
            nameDataSubSector = this.nameDataSubSector ?: "",
            sectorNameDataSubSector = this.sectorNameDataSubSector ?: "",
            statusDataSubSector = this.statusDataSubSector ?: "",
            fieldAssistantDataSubSector = this.fieldAssistantNameDataSubSector ?: "",
            commoditiesDataSubSector = this.commoditiesDataSubSector?.map { it.toCommoditiesDetailNotification() }
                .orEmpty(),
            data = this.data?.toDataRejected() ?: DataRejected("", "")
        )
}

data class CommoditiesDetailNotificationItem(
    @field:SerializedName("id")
    val idCommodities: String? = "",

    @field:SerializedName("name")
    val nameCommodities: String? = ""
) {
    fun toCommoditiesDetailNotification() =
        CommoditiesDetailNotification(
            idCommodities = this.idCommodities ?: "",
            nameCommodities = this.nameCommodities ?: ""
        )
}

data class DataRejectedItem(
    @field:SerializedName("title")
    val title: String? = "",

    @field:SerializedName("description")
    val description: String? = ""
) {
    fun toDataRejected() = DataRejected(title.orEmpty(), description.orEmpty())
}
