package com.agree.ecosystem.core.utils.utility.validation.rules

import android.view.View
import com.devbase.presentation.validation.rules.BaseRule
import com.telkom.legion.component.chips.base.LgnChip
import com.telkom.legion.component.chips.base.LgnChipGroup
import java.util.regex.Pattern

class ChipRule(override val errorMessage: String) : BaseRule {
    override fun validate(view: View): Boolean = regexMatch(extractInputTypedView(view))

    private fun regexMatch(input: String): Boolean {
        val pattern = Pattern.compile("^.{1,}\$")
        return pattern.matcher(input).matches()
    }

    private fun extractInputTypedView(view: View): String {
        return when (view) {
            is LgnChip -> {
                view.text.toString()
            }
            is LgnChipGroup -> {
                view.text
            }
            else -> {
                ""
            }
        }
    }
}
