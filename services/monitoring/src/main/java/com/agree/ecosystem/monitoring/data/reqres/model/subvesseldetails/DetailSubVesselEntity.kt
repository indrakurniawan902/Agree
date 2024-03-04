package com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel

@Entity
data class DetailSubVesselEntity(
    val activityName: String,
    val bruto: String,
    val code: String,
    val commodityId: String,
    val commodityName: String,
    val commodityVarietyId: String,
    val companyId: String,
    val companyMemberId: String,
    val companySubMemberId: String,
    val createdAt: String,
    val createdBy: String,
    val cycleCount: Int,
    val datePlanted: String,
    val districtId: String,
    val districtName: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val isDeleted: Boolean,
    val modifiedAt: String,
    val modifiedBy: String,
    val netto: String,
    val order: String,
    val population: String,
    val size: String,
    val sopId: String,
    val status: String,
    val subSectorId: String,
    val subVesselName: String,
    val target: String,
    val varietyName: String,
    val vesselId: String,
    val workerId: String,
    val workerName: String,
    val recordType: String,
    val hasSmartfarm: Boolean,
) : EntityModel {
    fun toDetailSubVessel(): DetailSubVessel {
        return DetailSubVessel(
            id = id,
            vesselId = vesselId,
            companyMemberId = companyMemberId,
            companySubMemberId = companySubMemberId,
            subSectorId = subSectorId,
            commodityId = commodityId,
            commodityVarietyId = commodityVarietyId,
            districtId = districtId,
            districtName = districtName,
            order = order,
            status = status,
            datePlanted = datePlanted,
            cycleCount = cycleCount,
            population = population,
            target = target,
            bruto = bruto,
            netto = netto,
            companyId = companyId,
            size = size,
            createdAt = createdAt,
            createdBy = createdBy,
            modifiedAt = modifiedAt,
            code = code,
            sopId = sopId,
            activityName = activityName,
            commodityName = commodityName,
            isDeleted = isDeleted,
            subVesselName = subVesselName,
            modifiedBy = modifiedBy,
            varietyName = varietyName,
            workerId = workerId,
            workerName = workerName,
            recordType = recordType,
            hasSmartfarm = hasSmartfarm,
        )
    }
}
