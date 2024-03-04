package com.agree.ecosystem.finances.data.reqres

import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanBodyPost
import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanCultivatorItem
import com.agree.ecosystem.finances.data.reqres.model.CultivatorBorrowerItem
import com.agree.ecosystem.finances.data.reqres.model.DetaiLoanPackageItem
import com.agree.ecosystem.finances.data.reqres.model.FormFieldSchemaItem
import com.agree.ecosystem.finances.data.reqres.model.HasLoanDataItem
import com.agree.ecosystem.finances.data.reqres.model.LoanPackageItem
import com.agree.ecosystem.finances.data.reqres.model.MyLoanDataItem
import com.agree.ecosystem.finances.data.reqres.model.ProfileFarmerItem
import com.devbase.data.source.DevRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable

/**
 * Finance Repository are Finance Data Contract Abstraction
 * on Data Layer
 * @author: @telkomdev-abdul
 * @since: 10 Jan 2023
 */
interface FinanceRepository : DevRepository {
    fun fetchListLoanPackage(source: String): Flowable<List<LoanPackageItem>>
    fun fetchDetailLoanPackage(loanPackageId: String): Flowable<DetaiLoanPackageItem>
    fun fetchListFarmerPartnership(userId: String): Flowable<List<CultivatorBorrowerItem>>
    fun fetchListActiveLoan(userId: String): Flowable<List<MyLoanDataItem>>
    fun fetchProfileCultivator(id: String): Flowable<ProfileFarmerItem>
    fun fetchCheckMemberEligibility(
        mitraId: String,
        loanPackageId: String
    ): Flowable<List<ApplyLoanCultivatorItem>>

    fun getDynamicFormCultivatorProfileData(
        borrowerId: String,
        schemeName: String
    ): Flowable<Map<String, FormFieldSchemaItem>>

    fun submitLoan(applyLoanBodyPost: ApplyLoanBodyPost): Flowable<JsonElement>
    fun checkIfHasLoan(borrowerId: String): Flowable<HasLoanDataItem>
}
