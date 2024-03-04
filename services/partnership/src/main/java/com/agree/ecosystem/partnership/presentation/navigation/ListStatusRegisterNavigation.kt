package com.agree.ecosystem.partnership.presentation.navigation

import androidx.navigation.NavController

interface ListStatusRegisterNavigation {
    fun getNavController(): NavController?

    fun goToPrevious()

    fun fromStatusRegistrationToDetailStatus(id: String)

    fun fromHomeToCompanies()

    fun fromDetailInboxToDetailStatus(id: String)
}
