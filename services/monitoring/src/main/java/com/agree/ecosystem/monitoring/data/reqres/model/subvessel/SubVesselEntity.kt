package com.agree.ecosystem.monitoring.data.reqres.model.subvessel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel

@Entity
data class SubVesselEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val vesselId: String,
    val companyMemberId: String,
    val companySubMemberId: String,
    val companyName: String,
    val subSectorId: String,
    val commodityId: String,
    val commodityVarietyId: String,
    val commodityName: String,
    val districtId: String,
    val districtName: String,
    val subVesselName: String,
    val order: String,
    val status: SubVessel.Status,
    val datePlanted: String,
    val companyId: String,
    val size: String,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val modifiedBy: String,
    val code: String,
    val target: Double,
    val varietyName: String,
    val vesselName: String,
    val workerName: String,
    val description: String,
    val sectorName: String,
    val sectorId: String,
    val name: String,
    val hasSmartfarm: Boolean,

) : EntityModel {
    fun toSubVessel(): SubVessel {
        return SubVessel(
            id = id,
            vesselId = vesselId,
            companyMemberId = companyMemberId,
            companySubMemberId = companySubMemberId,
            companyName = companyName,
            subSectorId = subSectorId,
            commodityId = commodityId,
            commodityVarietyId = commodityVarietyId,
            commodityName = commodityName,
            districtId = districtId,
            districtName = districtName,
            subVesselName = subVesselName,
            order = order,
            status = status,
            datePlanted = datePlanted,
            companyId = companyId,
            size = size,
            createdAt = createdAt,
            createdBy = createdBy,
            modifiedAt = modifiedAt,
            modifiedBy = modifiedBy,
            code = code,
            sectorId = sectorId,
            workerName = workerName,
            description = description,
            sectorName = sectorName,
            target = target,
            vesselName = vesselName,
            varietyName = varietyName,
            name = name,
            hasSmartfarm = hasSmartfarm
        )
    }
}
