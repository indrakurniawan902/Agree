package com.agree.ecosystem.finances.domain.reqres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class LoanPackage(
    val mitraId: String,
    val companyId: String,
    val companyName: String,
    val companyImage: String,
    val subsector: List<FinanceSubSectorLoanPackage>,
    val loanPackage: List<LoanPackageCompany>
) : Parcelable

@Keep
@Parcelize
data class FinanceSubSectorLoanPackage(
    val sectorId: String,
    val sectorName: String,
    val subSectorId: String,
    val subSectorName: String
): Parcelable

@Keep
@Parcelize
data class LoanPackageCompany(
    val loanPackageId: String,
    val loanPackageName: String,
    val image: String,
    val loanPackageFor: String,
    val loanPackageMaxAmount: Double,
    val loanPackageMinAmount: Double,
    var loanPackageType: String,
    val loanPackagePaymentType: String
) : Parcelable
