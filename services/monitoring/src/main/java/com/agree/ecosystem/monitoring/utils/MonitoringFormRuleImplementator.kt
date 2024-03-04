package com.agree.ecosystem.monitoring.utils

import android.view.View
import com.agree.ecosystem.core.utils.utility.validation.rules.AgrCheckableRule
import com.agree.ecosystem.monitoring.R
import com.agree.ui.domain.model.DynamicFormSchema
import com.devbase.presentation.validation.rules.BaseRule
import com.devbase.presentation.validation.util.minxMaxLengthRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.base.LgnTextField

class MonitoringFormRuleImplementator(val tagFormDataMap: Map<Any, DynamicFormSchema>) {

    fun implementTextFieldRule(
        form: View,
        errorMsg: Map<MonitoringFormValidationErrorType, String> = mapOf()
    ): Array<BaseRule> {
        val rules: MutableList<BaseRule> = mutableListOf()
        val minCharLength = tagFormDataMap[form.tag]?.min ?: 0
        val maxCharLength = tagFormDataMap[form.tag]?.max ?: 0

        when (form) {
            is LgnTextField -> {
                if (tagFormDataMap[form.tag]?.required == true) {
                    rules.add(
                        notEmptyRule(
                            errorMsg[MonitoringFormValidationErrorType.NOT_EMPTY_ERROR]
                                ?: getStringResource(R.string.not_empty_default_error)
                        )
                    )
                }
                if ((minCharLength != maxCharLength) && minCharLength != 0) {
                    rules.add(
                        minxMaxLengthRule(
                            minCharLength,
                            maxCharLength,
                            errorMsg[MonitoringFormValidationErrorType.MIN_MAX_ERROR]
                                ?: "Data ini harus terdiri dari $minCharLength-$maxCharLength karakter"
                        )
                    )
                }
            }
        }
        return rules.toTypedArray()
    }

    fun implementRadioOrCheckBoxRule(
        form: View,
        errorMsg: Map<MonitoringFormValidationErrorType, String> = mapOf()
    ): BaseRule? {
        return if (tagFormDataMap[form.tag]?.required == true) {
            when (form) {
                is LgnRadioContainer, is LgnCheckBoxContainer, is LgnChipContainer -> {
                    AgrCheckableRule(
                        errorMsg[MonitoringFormValidationErrorType.NOT_EMPTY_ERROR]
                            ?: getStringResource(R.string.not_checked_default_error)
                    )
                }
                else -> return null
            }
        } else null
    }
}
