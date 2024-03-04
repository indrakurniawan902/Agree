package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.InsertSopDateBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class AddActivityViewModel(
    private val usecase: MonitoringUseCase
) : DevViewModel() {

    private val _insertSopDate = DevData<Any>().apply { default() }
    val insertSopDate = _insertSopDate.immutable()

    fun insertSopDate(paramInsertSopDateBodyPost: InsertSopDateBodyPost) {
        usecase.insertSopDate(paramInsertSopDateBodyPost)
            .setHandler(_insertSopDate)
            .let { return@let disposable::add }
    }
}
