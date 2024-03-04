package com.agree.ecosystem.core.utils.utility.validation.rules

import android.view.View
import com.devbase.presentation.validation.rules.BaseRule
import com.telkom.legion.component.checkbox.base.LgnCheckBox
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.checkbox.base.LgnCheckBoxGroup
import com.telkom.legion.component.chips.base.LgnChip
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.chips.base.LgnChipGroup
import com.telkom.legion.component.radio.base.LgnRadioButton
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.radio.base.LgnRadioGroup

/**
 * Rule for Agree Checkable Component
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
class AgrCheckableRule(override val errorMessage: String) : BaseRule {
    override fun validate(view: View): Boolean = extractCheckedView(view)

    private fun extractCheckedView(view: View): Boolean {
        return when (view) {
            is LgnChip -> {
                view.isChecked
            }
            is LgnChipGroup -> {
                view.isChecked
            }
            is LgnChipContainer -> {
                view.isChecked
            }
            is LgnRadioButton -> {
                view.isChecked
            }
            is LgnRadioGroup -> {
                view.isChecked
            }
            is LgnRadioContainer -> {
                view.isChecked
            }
            is LgnCheckBox -> {
                view.isChecked
            }
            is LgnCheckBoxGroup -> {
                view.isChecked
            }
            is LgnCheckBoxContainer -> {
                view.isChecked
            }
            else -> {
                false
            }
        }
    }
}
