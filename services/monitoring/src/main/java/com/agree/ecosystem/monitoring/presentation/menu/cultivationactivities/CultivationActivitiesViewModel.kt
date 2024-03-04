package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.networkError
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.MonthItem
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParams
import com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar.EventDotCalendar
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.devbase.data.source.DevData
import com.devbase.data.source.VmData
import com.devbase.data.utilities.DevViewModel
import com.devbase.data.utilities.rx.transformers.flowableScheduler
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber

class CultivationActivitiesViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    var page = 1

    private val _activities = DevData<List<ActivitySop>>().apply { default() }
    val activities = _activities.immutable()

    private val _listMonth = DevData<List<MonthItem>>().apply { default() }
    val listMonth = _listMonth.immutable()

    private val _eventsDotCalendar = DevData<List<EventDotCalendar>>().apply { default() }
    val eventsDotCalendar = _eventsDotCalendar.immutable()

    private val _subVessel = DevData<DetailSubVessel>().apply { default() }
    val subVessel = _subVessel.immutable()

    fun getListActivitySop(subVesselId: String, date: String, sopRecordType: String? = null, block: (Int.(String?) -> Unit)) {
        Flowable.zip(
            useCase.getActivity(
                subVesselId, "'$date'", sopRecordType,
            ).compose(flowableScheduler()),
            useCase.getActivityEngine(
                CrudEngineParams(
                    "aktivitas_tambahan",
                    "a.subvessel_id = '$subVesselId' AND a.date_time::date = '$date' " +
                        "AND a.status = 'additional' " +
                        "GROUP BY a.id, p.name, pa.desc, pa.repetition, pa.detail ORDER BY a.order",
                    subVesselId,
                    date
                )
            ).compose(flowableScheduler())
        ) { activities, additionalActivities ->
            listOf(
                *activities.toTypedArray(),
                *additionalActivities
                    .onEach { it.isAdditional = true }
                    .toTypedArray()
            )
        }.compose(flowableScheduler())
            .cache()
            .onBackpressureBuffer()
            .doOnSubscribe {
                it.request(Long.MAX_VALUE)
            }
            .subscribeWith(object : DisposableSubscriber<List<ActivitySop>>() {
                override fun onStart() {
                    super.onStart()
                    _activities.loading()
                }

                override fun onNext(data: List<ActivitySop>) {
                    // Handle Activities
                    if (data.isNotEmpty()) {
                        _activities.success(data)
                    } else {
                        _activities.empty()
                    }

                    onComplete()
                }

                override fun onError(t: Throwable) {
                    t.printStackTrace()
                    val error = t.networkError()

                    block.invoke(error.second, error.first.message)

                    onComplete()
                }

                override fun onComplete() {
                    _activities.default()
                }
            }).let { return@let disposable::add }
    }

    fun getEventDotCalendar(subVesselId: String, block: (Int.(String?) -> Unit)) {
        useCase.getEventDotCalendar(
            subVesselId
        )
            .setHandler(_eventsDotCalendar, block)
            .let { return@let disposable::add }
    }

    fun getListMonth(monthSelected: Int) {
        val list1: List<MonthItem> = (1..12).map { MonthItem(month = it, selected = it == monthSelected) }
        _listMonth.value = VmData.success(list1)
    }

    fun getDetailSubVessel(subVesselId: String, block: (Int.(String?) -> Unit)) {
        useCase.getDetailSubVessel(subVesselId)
            .setHandler(_subVessel, block)
            .let { return@let disposable::add }
    }
}
