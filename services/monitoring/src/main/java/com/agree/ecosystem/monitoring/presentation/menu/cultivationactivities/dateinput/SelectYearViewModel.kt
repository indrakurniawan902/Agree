package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.devbase.data.source.DevData
import com.devbase.data.source.VmData
import com.devbase.data.utilities.DevViewModel
import java.time.LocalDate

class SelectYearViewModel(
    private val useCase: MonitoringUseCase
) : DevViewModel() {
    private val _listYear = DevData<List<Int>>().apply { default() }
    val listYear = _listYear.immutable()
    fun getListYear() {
        var list: List<Int> = (
            LocalDate.now().minusYears(10).year..LocalDate.now().plusYears(10).year
            ).map { it }
        _listYear.value = VmData.success(list)
    }
}
