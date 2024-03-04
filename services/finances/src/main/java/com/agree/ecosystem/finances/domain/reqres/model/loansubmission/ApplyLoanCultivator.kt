package com.agree.ecosystem.finances.domain.reqres.model.loansubmission

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ApplyLoanCultivator(
    val borrowerId: String,
    val farmerId: String,
    val name: String,
    val nik: String,
    val image: String,
    val loanPackageMaxAmount: Double,
    val loanPackageMinAmount: Double,
    val isEligible: Boolean,
    val isEligibleErrorMessage: List<String>? = null,
    val isEligibleIncompleteDataGroup: List<String>? = null,
    var plantingSeasonData: List<PlantingSeasonData>,
    val imageBW: String,
    val collateralData: List<ApplyLoanCollateralData>? = null
) : Parcelable

@Keep
@Parcelize
data class PlantingSeasonData(
    val landName: String,
    val landId: String,
    val landCode: String,
    val plantingSeasonId: String,
    val plantingSeasonStartDate: String,
    val size: Float,
    val budgetPlanId: String,
    val budgetPlanTotalPrice: String
) : Parcelable

@Keep
@Parcelize
data class ApplyLoanCollateralData(
    val collateralId: String,
    val collateralType: String,
    val collateralName: String,
    val collateralImage: String,
    val collateralIsInUse: Boolean
) : Parcelable

