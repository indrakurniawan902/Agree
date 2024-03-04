package com.agree.ecosystem.finances.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams

interface LoanSubmissionNavigation {

    fun getStartActivity()

    fun getNavController(): NavController?
    fun toTnc()
    fun navigateUp()

    fun toLandSubmissionPage()

    fun toNominalSubmissionPage()

    fun toCollateralPage()

    fun submissionSuccessNavigation()

    fun toProfileCultivator(
        params: ProfileCultivatorParams
    )

    fun toDetailBudgetPlant(item: String)

}
