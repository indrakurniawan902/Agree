package com.agree.ecosystem.partnership.presentation.navigation

import androidx.navigation.NavController

class StatusRegisterNavigationImpl(
    private val nav: NavController?
) : StatusRegisterNavigation {
    override fun getNavController(): NavController? {
        return nav
    }

    override fun goToPrevious() {
        nav?.navigateUp()
    }
}
