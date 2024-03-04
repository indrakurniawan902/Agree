package com.agree.ecosystem.partnership.presentation.menu.registration

import com.agree.ecosystem.partnership.domain.reqres.model.unittype.UnitType
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation

interface RegistrationDataContract {

    fun onGetUnitTypeLoading()

    fun onGetUnitTypeSuccess(data: List<UnitType>)

    fun onGetUnitTypeEmpty()

    fun onGetUnitTypeFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onValidationLoading()

    fun onValidationSuccess(data: Validation)

    fun onValidationEmpty()

    fun onValidationFailed(e: Throwable?) {
        // Do Nothing
    }
}
