package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.networkError
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParams
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.data.utilities.rx.transformers.flowableScheduler
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber

class ActivitiesViewModel(
    private val usecase: MonitoringUseCase
) : DevViewModel() {

    private val _activities = DevData<List<ActivitySop>>().apply { default() }
    val activities = _activities.immutable()

    fun getActivities(
        subVesselId: String,
        date: String,
        sopRecordType: String? = null,
        block: (Int.(String?) -> Unit)
    ) {
        Flowable.zip(
            usecase.getActivity(
                subVesselId, "'$date'", sopRecordType
            ).compose(flowableScheduler()),
            usecase.getActivityEngine(
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
//    private val _activities = DevData<List<ActivitySop>>().apply { default() }
//    val activities = _activities.immutable()
//
//    fun getActivities(
//        subVesselId: String,
//        date: String,
//        block: (Int.(String?) -> Unit)
//    ) {
//
//        Flowable.zip(
//            usecase.getActivity(
//                subVesselId, "'$date'"
//            ).compose(flowableScheduler()),
//            usecase.getActivityEngine(
//                CrudEngineParams(
//                    "aktivitas_tambahan",
//                    "a.subvessel_id = '$subVesselId' AND a.date_time::date = '$date' " +
//                        "AND a.status = 'additional' " +
//                        "GROUP BY a.id, p.name, pa.desc, pa.detail ORDER BY a.order"
//                )
//            ).compose(flowableScheduler())
//        ) { activities, additionalActivities ->
//            listOf(
//                *activities.toTypedArray(),
//                *additionalActivities
//                    .onEach { it.isAdditional = true }
//                    .toTypedArray()
//            )
//        }
//            .compose(flowableScheduler())
//            .cache()
//            .onBackpressureBuffer()
//            .doOnSubscribe {
//                it.request(Long.MAX_VALUE)
//            }
//            .subscribeWith(object :
//                    DisposableSubscriber<List<ActivitySop>>() {
//                    override fun onStart() {
//                        super.onStart()
//                        _activities.loading()
//                    }
//
//                    override fun onNext(data: List<ActivitySop>) {
//                        // Handle Activities
//                        if (data.isNotEmpty()) {
//                            _activities.success(data)
//                        } else {
//                            _activities.empty()
//                        }
//                        onComplete()
//                    }
//
//                    override fun onError(t: Throwable) {
//                        t.printStackTrace()
//                        val error = t.networkError()
//
//                        block.invoke(error.second, error.first.message)
//
//                        onComplete()
//                    }
//
//                    override fun onComplete() {
//                        _activities.default()
//                    }
//                }).let { return@let disposable::add }
//    }
}
