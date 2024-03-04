package com.agree.ecosystem.finances.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.finances.domain.reqres.model.params.DynamicFormProfileCultivatorParams
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams

interface ProfileCultivatorNavigation {

    fun backToPreviousActivity()

    fun getNavController(): NavController?

    fun navigateUp()

    fun fromListCultivatorToProfileCultivator(
        params: ProfileCultivatorParams
    )

    fun toDynamicFormProfileCultivator(
        param: DynamicFormProfileCultivatorParams
    )
}
