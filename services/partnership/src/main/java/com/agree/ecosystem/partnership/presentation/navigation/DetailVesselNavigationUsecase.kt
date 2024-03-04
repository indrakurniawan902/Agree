package com.agree.ecosystem.partnership.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel

interface DetailVesselNavigationUsecase {
    fun getNavController(): NavController?

    fun goToPrevious()

    fun fromDetailVesselToDetailSubVessel(subVessel: SubVessel)
}
