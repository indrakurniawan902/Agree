package com.agree.ecosystem.utilities.presentation.menu.sectorsdialog

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class SectorsViewModel(
    private val usecase: UtilitiesUsecase
) : DevViewModel() {

    private val _subSectors = DevData<List<SubSector>>().apply { default() }
    val subSectors = _subSectors.immutable()

    fun getSubSectors() {
        usecase.getSubSectors()
            .setHandler(_subSectors)
            .let { return@let disposable::add }
    }
}
