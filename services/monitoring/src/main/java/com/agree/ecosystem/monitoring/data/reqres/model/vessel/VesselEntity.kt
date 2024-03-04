package com.agree.ecosystem.monitoring.data.reqres.model.vessel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel

@Entity
data class VesselEntity(
    val address: String,
    val code: String,
    val companyId: String,
    val companyMemberId: String,
    val companyMemberWorkerId: String,
    val createdAt: String,
    val createdBy: String,
    val districtId: String,
    val districtName: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val modifiedAt: String,
    val modifiedBy: String,
    val name: String,
    val provinceId: String,
    val provinceName: String,
    val realizationSize: Float,
    val size: Float,
    val subdistrictId: String,
    val villageId: String,
    val workerId: String
) : EntityModel {
    fun toVessel(): Vessel {
        return Vessel(
            address = address,
            code = code,
            companyId = companyId,
            companyMemberId = companyMemberId,
            companyMemberWorkerId = companyMemberWorkerId,
            createdAt = createdAt,
            createdBy = createdBy,
            districtId = districtId,
            districtName = districtName,
            id = id,
            modifiedAt = modifiedAt,
            modifiedBy = modifiedBy,
            name = name,
            provinceId = provinceId,
            provinceName = provinceName,
            realizationSize = realizationSize,
            size = size,
            subdistrictId = subdistrictId,
            villageId = villageId,
            workerId = workerId
        )
    }
}
