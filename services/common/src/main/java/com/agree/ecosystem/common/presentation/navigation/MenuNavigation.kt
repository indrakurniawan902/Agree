package com.agree.ecosystem.common.presentation.navigation

import androidx.navigation.NavController
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector

interface MenuNavigation {

    fun getNavController(): NavController?

    fun goToPrevious()

    fun fromSettingsToProfile()

    fun fromSettingsToTermsConditions()

    fun fromSettingsToAboutAgree()

    fun fromSettingsToHelp()

    fun fromSettingsToOfflineMonitoring()

    fun fromHomeToHelp()

    fun fromHomeToCompanies()

    fun fromHomeToAgreepedia()

    fun fromHomeToAgreepediaDetail(article: Article)

    fun fromHomeToCompanyPartnerDetail(id: String)

    fun fromHomeToDetailSubVessel(id: String, sectorId: String)

    fun fromHomeToFinance(tab: Int)

    fun fromHomeToDetaiActiveLoan()

    fun fromVesselToDetailMonitoring(id: String)

    fun fromListSubVesselToCompanies()

    fun fromListVesselToCompanies()

    fun fromNotificationListToDetailNotification(inboxId: Inbox)

    fun fromDetailNotificationToDetailStatusPartnership(idSubmission: String)

    fun fromDetailNotificationToHistory(idSubVessel: String, createdAt: String, stateInbox: String)

    fun fromHomeSectorToListSubVessel()

    fun fromPartnershipToDetailPartnership(companyId: String)

    fun fromPartnershipToStatusRegistration()

    fun fromPartnershipToCompanies(subSector: List<SubSector>)

    fun fromDetailPartnershipToDetailCompany(companyId: String)

    fun fromMenuToChangePassword()

    fun fromMenuToLogin()
}
