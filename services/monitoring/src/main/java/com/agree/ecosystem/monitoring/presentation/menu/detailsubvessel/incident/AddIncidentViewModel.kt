package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.incident.AddIncidentBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.category.IncidentCategory
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.AddIncident
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import okhttp3.MultipartBody

class AddIncidentViewModel(
    private val usecase: MonitoringUseCase,
) : DevViewModel() {
    private val _addIncident = DevData<AddIncident>().apply { default() }
    val addIncident = _addIncident.immutable()

    private val _listCategory = DevData<List<IncidentCategory>>().apply { default() }
    val listCategory = _listCategory.immutable()

    fun addIncident(data: AddIncidentBodyPost, images: List<MultipartBody.Part>) {
        usecase.addNewIncident(
            data, images
        ).setHandler(_addIncident).let { return@let disposable::add }
    }

    fun getListCategory(companyId: String, commodityId: String) {
        usecase.getIncidentCategories(
            companyId, commodityId
        ).setHandler(_listCategory).let { return@let disposable::add }
    }
}
