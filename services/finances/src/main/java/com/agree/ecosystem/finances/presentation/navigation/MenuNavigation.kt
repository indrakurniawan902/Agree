package com.agree.ecosystem.finances.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage

interface MenuNavigation {
    fun toLoanSubmissionActivity(
        loanPackage: DetailLoanPackage,
        loanPackageType: String?,
        requiredField: ArrayList<FinanceCustomMapData>?,
        from: String,
        companyId: String,
        mitraId: String
    )

    fun getNavController(): NavController?
    fun fromListPackageToDetailPackage(loanPackageId: String, companyId: String, mitraId: String)
    fun fromListPackageToCompanies(subSector: Array<SubSector>)
}
