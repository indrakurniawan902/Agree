package com.agree.ecosystem.core.utils.domain.reqres.model

import android.view.View

data class ValidationModel(
    val view: View,
    val isValid: Boolean,
    val errorMessage: String
)
