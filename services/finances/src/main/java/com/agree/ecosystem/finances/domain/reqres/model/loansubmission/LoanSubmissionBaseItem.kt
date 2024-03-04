package com.agree.ecosystem.finances.domain.reqres.model.loansubmission

import com.agree.ecosystem.finances.domain.reqres.model.LoanPackageItemList
import com.agree.ecosystem.finances.utils.LoanSubmissionItemType

interface LoanSubmissionBaseItem {
    val viewType: LoanSubmissionItemType
    val fieldTag: String
}

data class CheckableCultivator(
    val data: ApplyLoanCultivator,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.CHECKABLE_CULTIVATOR
    var isChecked = false
}

data class DropdownLand(
    val data: ApplyLoanCultivator,
    var dropdownData: List<LoanSubmissionBaseItem>,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.DROPDOWN_LAND
    var isExpanded = true
}

data class CheckableLand(
    val farmerId: String,
    val data: PlantingSeasonData,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.CHECKABLE_LAND
    var isChecked = false
}

data class CheckableCollateral(
    val farmerId: String,
    val data: ApplyLoanCollateralData?,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    var isChecked = false
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.CHECKABLE_COLLATERAL
}

data class LoanAddCollateral(
    val farmerId: String,
    val data: ApplyLoanCultivator?,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    var isChecked = false
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.ADD_COLLATERAL
}

data class NominalCultivator(
    val data: ApplyLoanCultivator,
    var errorText: String,
    val nominalMax: Double,
    val nominalMin: Double,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.NOMINAL
    var nominal: String = ""
    var showError = false
}

data class GoodsCultivator(
    val data: LoanPackageItemList,
    var errorText: String,
    val nominalMax: Double,
    val nominalMin: Double,
    override val fieldTag: String = ""
) : LoanSubmissionBaseItem {
    override val viewType: LoanSubmissionItemType = LoanSubmissionItemType.GOODS
    var nominal: String = ""
    var loanItemRequest: Long = 0
    var showError = false
}
