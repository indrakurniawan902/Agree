package com.agree.ecosystem.common.presentation.menu.home

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.agree.ecosystem.agreepedia.domain.AgreepediaUsecase
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.common.domain.reqres.CompaniesUsecase
import com.agree.ecosystem.common.domain.reqres.FinanceUseCase
import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.users.domain.reqres.UsersUsecase
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.oratakashi.viewbinding.core.binding.livedata.liveData

class HomeViewModel(
    private val usersUsecase: UsersUsecase,
    private val partnershipUsecase: PartnershipUsecase,
    private val companiesUsecase: CompaniesUsecase,
    private val agreepediaUsecase: AgreepediaUsecase,
    private val financeUseCase: FinanceUseCase,
    private val utilitiesUsecase: UtilitiesUsecase
) : DevViewModel() {
    private val _profile = DevData<Profile>().apply { default() }
    val profile = _profile.immutable()

    fun getProfile(userId: String, block: (Int.(String?) -> Unit)) {
        usersUsecase.getProfile(userId)
            .setHandler(_profile, block)
            .let { return@let disposable::add }
    }

    private val _subSectors = DevData<List<SubSector>>().apply { default() }
    val subSectors = _subSectors.immutable()

    fun getSubSectors(block: (Int.(String?) -> Unit)) {
        utilitiesUsecase.getSubSectors()
            .setHandler(_subSectors, block)
            .let { return@let disposable::add }
    }

    private val _companyPartner = DevData<List<CompanyPartner>>().apply { default() }
    val companyPartner = _companyPartner.immutable()

    fun getCompanyPartner() {
        companiesUsecase.getCompanyPartner()
            .setHandler(_companyPartner)
    }

    private val _articles = DevData<List<Article>>().apply { default() }
    val articles = _articles.immutable()

    fun getAgreepediaArticles() {
        agreepediaUsecase.getAgreepediaArticles(ArticleParams(1, 5, ""))
            .setHandler(_articles)
            .let { return@let disposable::add }
    }

    private val _loanActive = DevData<List<MyLoanActive>>().apply { default() }
    val loanActive get() = _loanActive.immutable()

    fun getListLoanActive(idUser: String) {
        financeUseCase.getListLoanActive(idUser)
            .setHandler(_loanActive)
            .let { return@let disposable::add }
    }

    private val _isModalActive: MutableLiveData<Boolean> by liveData()
    val isModalActive get() = _isModalActive.immutable()

    fun getModalActive() {
        financeUseCase.getModalActiveStatus()
            .subscribe({
                _isModalActive.postValue(it)
            }, {
                _isModalActive.postValue(false)
            })
            .let { return@let disposable::add }
    }
}
