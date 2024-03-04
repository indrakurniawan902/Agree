package com.agree.ecosystem.core.utils.utility.validation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ValidationViewModel(private val view: ValidationView) : ViewModel() {
    fun registerValidations(vararg validation: MutableStateFlow<ValidationModel>) {
        viewModelScope.launch {
            combine(*validation) { validation ->
                validation.forEach {
                    if (it.isValid) view.onFormValidated(it.view)
                    else view.onFormUnvalidated(it.view, it.errorMessage)
                }
                if (validation.all { it.isValid }) {
                    view.onAllFormValidated()
                }
            }.collect()
        }
    }
}
