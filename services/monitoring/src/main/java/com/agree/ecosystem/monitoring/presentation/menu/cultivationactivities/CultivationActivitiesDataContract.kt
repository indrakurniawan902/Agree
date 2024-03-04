package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.MonthItem
import com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar.EventDotCalendar
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface CultivationActivitiesDataContract : SubVesselDataContract {
    fun getActivities()
    fun getEventCalendar()
    fun getDetailSubVessel()

    fun onGetListActivitySuccess(data: List<ActivitySop>)
    fun onGetListActivityEmpty()
    fun onGetListActivityLoading()
    fun onGetListActivityFailed(e: Throwable?) {
        // Do Nothing
    }

    fun getListMonth(data: List<MonthItem>)

    fun onEventCalendarLoading()
    fun onEventCalendarSuccess(data: List<EventDotCalendar>)
    fun onEventCalendarEmpty()

    fun onDetailSubVesselLoading()
    fun onDetailSubVesselSuccess(data: DetailSubVessel)
}
