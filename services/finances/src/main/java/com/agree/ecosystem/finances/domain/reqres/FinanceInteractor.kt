package com.agree.ecosystem.finances.domain.reqres

import com.agree.ecosystem.finances.data.reqres.FinanceRepository
import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanBodyPost
import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.HasLoanData
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.MyLoanData
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FormFieldSchema
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.ProfileFarmer
import com.google.gson.JsonElement
import io.reactivex.Flowable

/**
 * Finance Interactor are Finance Usecase Implementation
 * on Domain Layer
 * @author: @telkomdev-abdul
 * @since: 10 Jan 2023
 */
class FinanceInteractor(
    private val repo: FinanceRepository
) : FinanceUsecase {
    override fun fetchListLoanPackage(source: String): Flowable<List<LoanPackage>> {
        return repo.fetchListLoanPackage(source).map {
            it.map { data ->
                data.toLoanPackageModel()
            }
        }
    }

    override fun fetchDetailLoanPackage(loanPackageId: String): Flowable<DetailLoanPackage> {
        return repo.fetchDetailLoanPackage(loanPackageId).map {
            it.toDetailLoanPackage()
        }
    }

    override fun fetchListCultivatorPartnership(userId: String): Flowable<List<CultivatorBorrower>> {
        return repo.fetchListFarmerPartnership(userId).map {
            it.map { data -> data.toFarmerBorrower() }
        }
    }

    override fun fetchListActiveLoan(userId: String): Flowable<List<MyLoanData>> {
        return repo.fetchListActiveLoan(userId).map {
            it.map {
                it.toMyLoanData()
            }
        }
    }

    override fun fetchProfileCultivator(id: String): Flowable<ProfileFarmer> {
        return repo.fetchProfileCultivator(id).map {
            it.toProfileFarmer()
        }
    }

    override fun fetchCheckMemberEliginility(
        mitraId: String,
        loanPackageId: String
    ): Flowable<List<ApplyLoanCultivator>> {
        return repo.fetchCheckMemberEligibility(mitraId, loanPackageId).map {
            it.map {
                it.toApplyLoanCultivator()
            }
        }
    }

    override fun fetchDynamicFormCultivatorProfileData(
        borrowerId: String,
        schemeName: String
    ): Flowable<Map<String, FormFieldSchema>> {
        return repo.getDynamicFormCultivatorProfileData(borrowerId, schemeName).map {
            it.mapValues {
                it.value.toFieldInfo()
            }
        }
    }

    override fun submitLoan(applyLoanBodyPost: ApplyLoanBodyPost): Flowable<JsonElement> {
        return repo.submitLoan(applyLoanBodyPost).map {
            it
        }
    }

    override fun checkIfHasLoan(borrowerId: String): Flowable<HasLoanData> {
        return repo.checkIfHasLoan(borrowerId).map {
            it.toHasLoanData()
        }
    }


}
