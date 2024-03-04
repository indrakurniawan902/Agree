package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class CultivationActivitiesObserver(
    private val view: CultivationActivitiesDataContract,
    private val viewModel: CultivationActivitiesViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.activities.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> {
                    view.onGetListActivityLoading()
                }
                is VmData.Success -> {
                    view.onGetListActivitySuccess(it.data)
                }
                is VmData.Failure -> {
                    view.onGetListActivityFailed(it.throwable)
                }
                is VmData.Empty -> {
                    view.onGetListActivityEmpty()
                }
            }
        }

        viewModel.listMonth.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Success -> {
                    view.getListMonth(it.data)
                }
                is VmData.Loading -> Unit
                is VmData.Empty -> Unit
                is VmData.Failure -> Unit
            }
        }

        viewModel.eventsDotCalendar.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Success -> {
                    view.onEventCalendarSuccess(it.data)
                }
                is VmData.Loading -> Unit
                is VmData.Empty -> {
                    view.onEventCalendarEmpty()
                }
                is VmData.Failure -> Unit
            }
        }

        viewModel.subVessel.observe(owner) {
            when (it) {
                is VmData.Loading -> view.onDetailSubVesselLoading()
                is VmData.Default -> Unit
                is VmData.Empty -> Unit
                is VmData.Success -> view.onDetailSubVesselSuccess(it.data)
                is VmData.Failure -> Unit
            }
        }
    }
}
