package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail

interface DetailActivitySopDataContract {
    fun initViewPager(data: List<SopActivityDetail>) {
        // Do Nothing
    }

    fun getActivityDetailData() {
        // Do Nothing
    }

    fun onGetSopActivityDetailLoading() {
        // Do Nothing
    }

    fun onGetSopActivityDetailSuccess(data: List<SopActivityDetail>) {
        // Do Nothing
    }

    fun onGetSopActivityDetailError(message: String?) {
        // Do Nothing
    }

    fun getValidationActivityDetail(subVesselId: String) {
        // Do Nothing
    }

    fun getValidationActivityDetailSuccess(data: ValidationActivityDetail) {
        // Do Nothing
    }

    fun getValidationActivityDetailError(message: String?) {
        // Do Nothing
    }

    fun getValidationActivityDetailFinish() {
        // Do Nothing
    }
}
