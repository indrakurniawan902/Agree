package com.agree.ecosystem.finances.domain.reqres

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
 * Finance Usecase are Finance Domain Contract Abstraction
 * on Domain Layer
 * @author: @telkomdev-abdul
 * @since: 10 Jan 2023
 */
interface FinanceUsecase {
    fun fetchListLoanPackage(source: String): Flowable<List<LoanPackage>>

    fun fetchDetailLoanPackage(loanPackageId: String): Flowable<DetailLoanPackage>

    fun fetchListCultivatorPartnership(userId: String): Flowable<List<CultivatorBorrower>>

    fun fetchListActiveLoan(userId: String): Flowable<List<MyLoanData>>

    fun fetchProfileCultivator(id: String): Flowable<ProfileFarmer>

    fun fetchCheckMemberEliginility(
        mitraId: String,
        loanPackageId: String
    ): Flowable<List<ApplyLoanCultivator>>

    fun fetchDynamicFormCultivatorProfileData(
        borrowerId: String,
        schemeName: String
    ): Flowable<Map<String, FormFieldSchema>>

    fun submitLoan(applyLoanBodyPost: ApplyLoanBodyPost): Flowable<JsonElement>

    fun checkIfHasLoan(borrowerId: String): Flowable<HasLoanData>

}
