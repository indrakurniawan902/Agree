package com.agree.ecosystem.finances.presentation.navigation

import android.app.Activity
import androidx.navigation.NavController
import com.agree.ecosystem.finances.domain.reqres.model.params.DynamicFormProfileCultivatorParams
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams
import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator.ListCultivatorFragmentDirections
import com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.ProfileCultivatorFragmentDirections

class ProfileCultivatorNavigationImpl(
    private val nav: NavController?,
    private val activity: Activity?
) : ProfileCultivatorNavigation {
    override fun backToPreviousActivity() {
        activity?.finish()
    }

    override fun getNavController() = nav
    override fun navigateUp() {
        nav?.navigateUp()
    }

    override fun fromListCultivatorToProfileCultivator(params: ProfileCultivatorParams) {
        runCatching {
            nav?.navigate(
                ListCultivatorFragmentDirections.actionListCultivatorFragmentToProfileCultivatorFragment(
                    params
                )
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun toDynamicFormProfileCultivator(param: DynamicFormProfileCultivatorParams) {
        runCatching {
            nav?.navigate(
                ProfileCultivatorFragmentDirections.actionProfileCultivatorFragmentToDynamicformInfoCultivatorFragment(
                    param
                )
            )
        }
    }
}
