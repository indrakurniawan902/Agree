package com.agree.ecosystem.monitoring.presentation.menu.insertupdateadditionalactivitysop

import android.view.View
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.InsertActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail
import com.agree.ecosystem.monitoring.utils.MonitoringFormRuleImplementator
import com.agree.ui.data.reqres.model.FormItem
import com.agree.ui.domain.model.DynamicFormSchema
import kotlinx.coroutines.flow.MutableStateFlow

interface InsertUpdateAdditionalActivityDataContract {
    fun extractValueThenInsertOrUpdate() {
        // Do Nothing
    }

    fun insertAdditionalActivity() {
        // Do Nothing
    }

    fun updateAdditionalActivity() {
        // Do Nothing
    }

    fun constructForm(formData: List<FormItem>) {
        // Do Nothing
    }

    fun setFormEnabled(isEditable: Boolean) {
        // Do Nothing
    }

    fun setSubFormListener(schema: List<DynamicFormSchema>) {
        // Do Nothing
    }

    fun addValidationRule(
        viewStatePair: Map.Entry<View, MutableStateFlow<ValidationModel>>,
        ruleImplementator: MonitoringFormRuleImplementator
    ) {
        // Do Nothing
    }

    fun onGetSopActivityDetailLoading() {
        // Do Nothing
    }

    fun onGetSopActivityDetailSuccess(data: List<AdditionalSopActivityDetail>) {
        // Do Nothing
    }

    fun onGetSopActivityDetailError(message: String?) {
        // Do Nothing
    }

    fun onInsertAdditionalActivityLoading() {
        // Do Nothing
    }

    fun onInsertAdditionalActivitySuccess(data: InsertActivitySop) {
        // Do Nothing
    }

    fun onInsertAdditionalActivityError(e: Throwable?) {
        // Do Nothing
    }

    fun onUpdateAdditionalActivityLoading() {
        // Do Nothing
    }

    fun onUpdateAdditionalActivitySuccess() {
        // Do Nothing
    }

    fun onUpdateAdditionalActivityError(e: Throwable?) {
        // Do Nothing
    }
}
