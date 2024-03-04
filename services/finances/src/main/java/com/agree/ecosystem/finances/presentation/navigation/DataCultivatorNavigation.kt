package com.agree.ecosystem.finances.presentation.navigation

import androidx.navigation.NavController

interface DataCultivatorNavigation {

    fun backToPreviousActivity()

    fun getNavController(): NavController?

    fun fromTabCultivatorDataToCultivatorDataActivity(mitraId: String)
}
