package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities

import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.SharedComponentDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface ActivitiesDataContract :
    SubVesselDataContract,
    SharedComponentDataContract {

    fun getActivities(id: String)
    fun onActivityLoading()
    fun onActivitySuccess(data: List<ActivitySop>)
    fun onActivityEmpty()
    fun onActivityFailed(e: Throwable?) {
        // Do Nothing
    }
}
