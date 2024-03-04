package com.agree.ui.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.checkbox.base.LgnCheckBoxGroup
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.chips.base.LgnChipGroup
import com.telkom.legion.component.image.MultiplePhotoField
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.radio.base.LgnRadioGroup
import com.telkom.legion.component.textfield.base.LgnTextField

object DynamicFormUtils {
    fun <T> findFormItemByTag(formContainer: ViewGroup, tag: Any): T? {
        return try {
            formContainer.children.find {
                it.tag == tag
            } as T
        } catch (e: ClassCastException) {
            null
        }
    }

    fun View.setFormEnabled(isEnable: Boolean): View {
        when (this) {
            is LgnTextField -> this.isEnable = isEnable

            is LgnRadioContainer -> {
                this.children.forEach { radioContainerItem ->
                    when (radioContainerItem) {
                        is ViewGroup -> {
                            radioContainerItem.children.forEach { viewGroupItem ->
                                when (viewGroupItem) {
                                    is LgnRadioGroup -> {
                                        viewGroupItem.children.forEach { radioGroupItem ->
                                            radioGroupItem.isEnabled = isEnable
                                        }
                                        return this
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is LgnCheckBoxContainer -> {
                this.children.forEach { checkboxContainerItem ->
                    when (checkboxContainerItem) {
                        is ViewGroup -> {
                            checkboxContainerItem.children.forEach { viewGroupItem ->
                                when (viewGroupItem) {
                                    is LgnCheckBoxGroup -> {
                                        viewGroupItem.children.forEach { checkboxGroupItem ->
                                            checkboxGroupItem.isEnabled = isEnable
                                        }
                                        return this
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is LgnChipContainer -> {
                this.children.forEach { chipsContainerItem ->
                    when (chipsContainerItem) {
                        is ViewGroup -> {
                            chipsContainerItem.children.forEach { viewGroupItem ->
                                when (viewGroupItem) {
                                    is LgnChipGroup -> {
                                        viewGroupItem.children.forEach { chipGroupItem ->
                                            chipGroupItem.isEnabled = isEnable
                                        }
                                        return this
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is MultiplePhotoField -> this.isEnable = isEnable
        }

        return this
    }
}
