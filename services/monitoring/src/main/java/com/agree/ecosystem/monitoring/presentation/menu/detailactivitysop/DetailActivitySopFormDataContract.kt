package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import android.view.View
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.monitoring.utils.MonitoringFormRuleImplementator
import com.agree.ui.data.reqres.model.FormItem
import com.agree.ui.domain.model.DynamicFormSchema
import kotlinx.coroutines.flow.MutableStateFlow

interface DetailActivitySopFormDataContract {
    fun extractValueThenUpdateOrInsert() {
        // Do Nothing
    }

    fun setFormEnabled(isEditable: Boolean) {
        // Do Nothing
    }

    fun constructForm(formData: List<FormItem>) {
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

    fun updateSopActivityDetailData() {
        // Do Nothing
    }

    fun insertSopActivityDetailData() {
        // Do Nothing
    }

    fun onInsertSopActivityDetailLoading() {
        // Do Nothing
    }

    fun onInsertSopActivityDetailSuccess() {
        // Do Nothing
    }

    fun onInsertSopActivityDetailError(e: Throwable?) {
        // Do Nothing
    }

    fun onUpdateSopActivityDetailLoading() {
        // Do Nothing
    }

    fun onUpdateSopActivityDetailSuccess() {
        // Do Nothing
    }

    fun onUpdateSopActivityDetailError(e: Throwable?) {
        // Do Nothing
    }

    fun onUpdateActivityDetailSopLoading() {
        // Do Nothing
    }

    fun onUpdateActivityDetailSopSuccess() {
        // Do Nothing
    }

    fun onUpdateActivityDetailSopError(e: Throwable?) {
        // Do Nothing
    }
}
