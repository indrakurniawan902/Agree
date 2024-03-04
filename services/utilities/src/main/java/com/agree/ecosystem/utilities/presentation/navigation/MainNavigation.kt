package com.agree.ecosystem.utilities.presentation.navigation

import com.agree.ecosystem.utilities.domain.reqres.model.help.Help

interface MainNavigation {
    fun goToPrevious()

    fun fromHelpToDetailHelp(data: Help)
}
