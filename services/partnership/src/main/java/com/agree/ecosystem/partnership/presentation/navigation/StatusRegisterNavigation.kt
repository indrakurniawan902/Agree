package com.agree.ecosystem.partnership.presentation.navigation

import androidx.navigation.NavController

interface StatusRegisterNavigation {
    fun getNavController(): NavController?

    fun goToPrevious()
}
