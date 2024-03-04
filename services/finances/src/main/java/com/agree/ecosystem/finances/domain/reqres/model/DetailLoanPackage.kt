package com.agree.ecosystem.finances.domain.reqres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DetailLoanPackage(
    val loanPackageId: String,
    val loanPackageName: String,
    val loanPackageDescription: String,
    val loanPackageMaxAmount: Double,
    val loanPackageMinAmount: Double,
    val loanPackagePaymentType: String,
    val loanPackageInstallmentType: String,
    val image: String,
    val loanPackageDataSchema: LoanPackageDataSchema,
    val loanPackageItemList: List<@RawValue LoanPackageItemList>
) : Parcelable
