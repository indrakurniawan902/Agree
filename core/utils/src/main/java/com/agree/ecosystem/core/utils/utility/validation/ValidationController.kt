package com.agree.ecosystem.core.utils.utility.validation

import android.view.View
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

interface ValidationController {
    val forms: MutableList<MutableStateFlow<ValidationModel>>
    val validation: ValidationViewModel

    fun registerFormState(vararg form: MutableStateFlow<ValidationModel>) {
        forms.addAll(form)
        validation.registerValidations(*forms.toTypedArray())
    }

    fun isValid(): Boolean? {
        return null
    }

    fun clearForm() {
        forms.clear()
    }

    val View.state: MutableStateFlow<ValidationModel>?
        get() {
            return forms.find { it.value.view == this }
        }
}
