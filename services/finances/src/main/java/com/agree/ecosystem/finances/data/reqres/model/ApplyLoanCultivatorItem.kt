package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCollateralData
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.PlantingSeasonData
import com.google.gson.annotations.SerializedName

data class ApplyLoanCultivatorItem(
    @SerializedName("borrowerId") val borrowerId: String?,
    @SerializedName("farmerId") val farmerId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("nik") val nik: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("loanPackageMaxAmount") val loanPackageMaxAmount: Double?,
    @SerializedName("loanPackageMinAmount") val loanPackageMinAmount: Double?,
    @SerializedName("isEligible") val isEligible: Boolean?,
    @SerializedName("isEligibleErrorMessage") val isEligibleErrorMessage: List<String>? = null,
    @SerializedName("isEligibleIncompleteDataGroup") val isEligibleIncompleteDataGroup: List<String>? = null,
    @SerializedName("plantingSeasonData") val plantingSeasonData: List<PlantingSeasonDataItem>?,
    @SerializedName("imageBW") val imageBW: String? = "",
    @SerializedName("collateralData") val collateralData: List<ApplyLoanCollateralDataItem>? = null
) {
    fun toApplyLoanCultivator(): ApplyLoanCultivator {
        return ApplyLoanCultivator(borrowerId = borrowerId ?: "",
            farmerId = farmerId ?: "",
            name = name ?: "",
            nik = nik ?: "",
            image = image ?: "",
            loanPackageMaxAmount = loanPackageMaxAmount.toString().toDoubleOrNull() ?: 0.0,
            loanPackageMinAmount = loanPackageMinAmount.toString().toDoubleOrNull() ?: 0.0,
            isEligible = isEligible ?: false,
            isEligibleErrorMessage = isEligibleErrorMessage,
            isEligibleIncompleteDataGroup = isEligibleIncompleteDataGroup,
            plantingSeasonData = plantingSeasonData?.map { it.toPlantingSeasonData() }
                ?: emptyList(),
            imageBW = imageBW ?: "",
            collateralData = collateralData?.map { it.toApplyLoanCollateralData() })
    }
}

data class PlantingSeasonDataItem(
    @SerializedName("landName") val landName: String?,
    @SerializedName("landId") val landId: String?,
    @SerializedName("landCode") val landCode: String?,
    @SerializedName("plantingSeasonId") val plantingSeasonId: String?,
    @SerializedName("plantingSeasonStartDate") val plantingSeasonStartDate: String?,
    @SerializedName("size") val size: Float?,
    @SerializedName("budgetPlanId") val budgetPlanId: String?,
    @SerializedName("budgetPlanTotalPrice") val budgetPlanTotalPrice: String?
) {
    fun toPlantingSeasonData(): PlantingSeasonData {
        return PlantingSeasonData(
            landName ?: "",
            landId ?: "",
            landCode ?: "",
            plantingSeasonId ?: "",
            plantingSeasonStartDate ?: "",
            size ?: 0f,
            budgetPlanId ?: "",
            budgetPlanTotalPrice ?: ""
        )
    }
}

data class ApplyLoanCollateralDataItem(
    @SerializedName("collateralId") val collateralId: String?,
    @SerializedName("collateralType") val collateralType: String?,
    @SerializedName("collateralName") val collateralName: String?,
    @SerializedName("collateralImage") val collateralImage: String?,
    @SerializedName("collateralIsInUse") val collateralIsInUse: Boolean?
) {
    fun toApplyLoanCollateralData(): ApplyLoanCollateralData {
        return ApplyLoanCollateralData(
            collateralId ?: "",
            collateralType ?: "",
            collateralName ?: "",
            collateralImage ?: "",
            collateralIsInUse ?: false
        )
    }
}