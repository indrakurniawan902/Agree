package com.agree.ui.builder

import android.content.Context
import com.telkom.legion.component.checkbox.LgnPrimaryCheckBoxContainer
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.chips.small.LgnPrimarySmallChipContainer
import com.telkom.legion.component.image.MultiplePhotoField
import com.telkom.legion.component.radio.LgnPrimaryRadioContainer
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.*
import com.telkom.legion.component.utility.enums.ChipsType

class Builder(
    private val context: Context
) {
    fun setupSingleTextField(
        block: LgnSingleField.() -> Unit
    ): LgnSingleField {
        return LgnSingleField(context).apply(block)
    }

    fun setupCalendarField(
        block: LgnCalendarField.() -> Unit
    ): LgnCalendarField {
        return LgnCalendarField(context).apply(block)
    }

    fun setupDropdownField(
        block: LgnDropdownField.() -> Unit
    ): LgnDropdownField {
        return LgnDropdownField(context).apply(block)
    }

    fun setupTextAreaField(
        block: LgnTextAreaField.() -> Unit
    ): LgnTextAreaField {
        return LgnTextAreaField(context).apply(block)
    }

    fun setupPasswordField(
        block: LgnPasswordField.() -> Unit
    ): LgnPasswordField {
        return LgnPasswordField(context).apply(block)
    }

    fun setupSingleUnitField(
        block: LgnSingleUnitField.() -> Unit
    ): LgnSingleUnitField {
        return LgnSingleUnitField(context).apply(block)
    }

    fun setupTimeField(
        block: LgnTimeField.() -> Unit
    ): LgnTimeField {
        return LgnTimeField(context).apply(block)
    }

    fun setupRadioField(
        block: LgnRadioContainer.() -> Unit
    ): LgnRadioContainer {
        return LgnPrimaryRadioContainer(context).apply(block)
    }

    fun setupMultiplePhotoField(
        block: MultiplePhotoField.() -> Unit
    ): MultiplePhotoField {
        return MultiplePhotoField(context).apply(block)
    }

    fun setupCheckBoxField(
        block: LgnCheckBoxContainer.() -> Unit
    ): LgnCheckBoxContainer {
        return LgnPrimaryCheckBoxContainer(context).apply(block)
    }

    fun setupChipField(
        block: LgnChipContainer.() -> Unit
    ): LgnChipContainer {
        return LgnPrimarySmallChipContainer(context).apply {
            chipType = ChipsType.FILTER_TYPE
            isSingleLine = false
        }.apply(block)
    }
}
