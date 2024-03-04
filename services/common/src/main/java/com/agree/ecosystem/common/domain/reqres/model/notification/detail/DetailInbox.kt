package com.agree.ecosystem.common.domain.reqres.model.notification.detail

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailInbox(
    val id: String,
    val companyId: String,
    val companyMemberid: String,
    val workerId: String,
    val subVesselId: String,
    val submissionId: String,
    val userId: String,
    val type: String,
    val title: String,
    val category: String,
    val description: String,
    val createdAt: String,
    val createdBy: String,
    val isRead: Boolean,
    val status: String,
    val data: DataDetailInbox
) : Parcelable

@Keep
@Parcelize
data class DataDetailInbox(
    val descriptionDataDetailInbox: String,
    val statusDataDetailInbox: String,
    val titleDataDetailInbox: String,
    val vesselsDataDetailInbox: List<VesselsLastResultRegistration>,
    val subSectorDataDetailInbox: List<DataSubSectorsDetailNotification>,
    val dataRejected: DataRejected
) : Parcelable

@Keep
@Parcelize
data class DataSubSectorsDetailNotification(
    val idDataSubSector: String,
    val nameDataSubSector: String,
    val sectorNameDataSubSector: String,
    val statusDataSubSector: String,
    val fieldAssistantDataSubSector: String,
    val commoditiesDataSubSector: List<CommoditiesDetailNotification>,
    val data: DataRejected
) : Parcelable

@Keep
@Parcelize
data class CommoditiesDetailNotification(
    val idCommodities: String,
    val nameCommodities: String
) : Parcelable

@Keep
@Parcelize
data class DataRejected(
    val title: String,
    val description: String
) : Parcelable
