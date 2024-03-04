package com.agree.ecosystem.partnership.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity

interface RegistrationNavigation {
    fun getNavController(): NavController?

    fun goToPrevious()

    fun fromRegistrationToAddAddress()
    fun fromRegistrationToStatusRegistration(data: RegistrationStatusDetails)

    fun fromCompanyListToCompanyDetail(id: String)
    fun fromCompanyDetailsToRegistration(data: Company, dataCommodity: List<Commodity>)

    fun fromRegistrationToTnc()
}
