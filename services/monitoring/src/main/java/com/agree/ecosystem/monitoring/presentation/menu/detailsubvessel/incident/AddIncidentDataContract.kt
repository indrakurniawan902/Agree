package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import com.agree.ecosystem.monitoring.domain.reqres.model.category.IncidentCategory

interface AddIncidentDataContract {

    fun onAddIncidentLoading()

    fun onAddIncidentSuccess()

    fun onAddIncidentFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onGetIncidentCategoriesLoading()

    fun onGetIncidentCategoriesSuccess(data: List<IncidentCategory>)

    fun onGetIncidentCategoriesFailed(e: Throwable?) {
        // Do Nothing
    }
}
