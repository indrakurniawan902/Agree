package com.agree.ecosystem.common.presentation.menu.home

import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector

interface HomeDataContract {
    fun onGetProfileLoading()
    fun onGetProfileSuccess(data: Profile)
    fun onGetProfileFailed(e: Throwable?) {
        // Do Nothing
    }

    fun getProfile()
    fun getSubSectors()
    fun getCompanyPartner()
    fun getAgreepediaArticles()

    // fun initServicesMenu()

    fun onGetSubSectorsLoading()
    fun onGetSubSectorsSuccess(data: List<SubSector>)
    fun onGetSubSectorsFailed(e: Throwable?)

    fun onGetCompanyPartnerLoading()
    fun onGetCompanyPartnerSuccess(data: List<CompanyPartner>)
    fun onGetCompanyPartnerFailed(e: Throwable?)

    fun onGetAgreepediaArticlesLoading()
    fun onGetAgreepediaArticlesSuccess(data: List<Article>)
    fun onGetAgreepediaArticlesEmpty()
    fun onGetAgreepediaArticlesFailed(e: Throwable?)

    fun getLoanActive(idUser: String)
    fun onGetLoanActiveLoading()
    fun onGetLoanActiveSuccess(data: List<MyLoanActive>)
    fun onGetLoanActiveEmpty()
    fun onGetLoanActiveFailed(e: Throwable?)
    fun getModalActiveStatus()
    fun onGetModalActiveSuccess(data: Boolean)
}
