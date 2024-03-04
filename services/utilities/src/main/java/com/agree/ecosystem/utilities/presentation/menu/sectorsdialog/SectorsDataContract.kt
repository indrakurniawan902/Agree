package com.agree.ecosystem.utilities.presentation.menu.sectorsdialog

import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector

interface SectorsDataContract {

    fun onGetSubSectorsLoading()

    fun onGetSubSectorsSuccess(data: List<SubSector>)

    fun onGetSubSectorsFailed(e: Throwable?)

    fun getSubSectors()
}
