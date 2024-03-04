package com.agree.ecosystem.finances.domain.reqres.model.profilefarmer

class ProfileFarmer(
    val borrowerId: String,
    val image: String,
    val placeOfBirth: String,
    val education: String,
    val gender: String,
    val detailsInfo: List<Map<String, FarmerInfoPercentage>>,
    val dateOfBirth: String,
    val balanceBorrower: Int,
    val nik: String,
    val farmerId: List<String>,
    val name: String,
    val isActiveLoan: Boolean = false,
    val isActiveLoanId: String? = "",
    val contactNumber: String,
    val expenditureBorrower: Int
)

data class FarmerInfoPercentage(
    val percentage: Int,
    val isCompleted: Boolean,
    val filledFields: Int,
    val mandatoryFields: Int
)

data class FarmerHasLoan(
    val hasLoan: Boolean = false
)
