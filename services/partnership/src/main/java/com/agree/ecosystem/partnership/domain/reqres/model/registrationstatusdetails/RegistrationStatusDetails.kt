package com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RegistrationStatusDetails(
    val id: String,
    val companyId: String,
    val size: Double,
    val type: String,
    val status: RegistrationStatus.Status,
    val address: String,
    val provinceId: String,
    val districtId: String,
    val subDistrictId: String,
    val villageId: String,
    val provinceName: String,
    val districtName: String,
    val subDistrictName: String,
    val villageName: String,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val companyLogo: String,
    val companyName: String,
    val companyStatus: String,
    val commodity: List<Commodity>,
    val subSectors: List<SubSector>,
    val description: String
) : Parcelable
