package com.agree.ecosystem.finances.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanBodyPost
import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanCultivatorItem
import com.agree.ecosystem.finances.data.reqres.model.CultivatorBorrowerItem
import com.agree.ecosystem.finances.data.reqres.model.DetaiLoanPackageItem
import com.agree.ecosystem.finances.data.reqres.model.FormFieldSchemaItem
import com.agree.ecosystem.finances.data.reqres.model.HasLoanDataItem
import com.agree.ecosystem.finances.data.reqres.model.LoanPackageItem
import com.agree.ecosystem.finances.data.reqres.model.MyLoanDataItem
import com.agree.ecosystem.finances.data.reqres.model.ProfileFarmerItem
import com.agree.ecosystem.finances.data.reqres.web.AgreeFinanceApi
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.google.gson.JsonElement
import io.reactivex.Flowable

/**
 * Finance Data Store are Finance Repository Implementation
 * on Data Layer
 * @author: @telkomdev-abdul
 * @since: 10 Jan 2023
 */
@WorkerThread
class FinanceDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreeFinanceApi
) : FinanceRepository {
    override fun fetchListLoanPackage(source: String): Flowable<List<LoanPackageItem>> {
        return webService.fetchListLoanPackage(source).lift(
            flowableApiError()
        ).map { it.data }
    }

    override fun fetchDetailLoanPackage(loanPackageId: String): Flowable<DetaiLoanPackageItem> {
        return webService.fetchDetailLoanPackage(loanPackageId).lift(
            flowableApiError()
        ).map { it.data }
    }

    override fun fetchListFarmerPartnership(userId: String): Flowable<List<CultivatorBorrowerItem>> {
        return webService.fetchListFarmerPartnership(userId).lift(
            flowableApiError()
        ).map { it.data }
    }

    override fun fetchListActiveLoan(userId: String): Flowable<List<MyLoanDataItem>> {
        return webService.fetchListActiveLoan(userId).lift(
            flowableApiError()
        ).map { it.data }
    }

    override fun fetchProfileCultivator(id: String): Flowable<ProfileFarmerItem> {
        return webService.fetchDetailBorrower(id).lift(
            flowableApiError()
        ).map {
            it.data
        }
    }

    override fun fetchCheckMemberEligibility(
        mitraId: String,
        loanPackageId: String
    ): Flowable<List<ApplyLoanCultivatorItem>> {
        return webService.checkMemberEligibility(mitraId, loanPackageId)
            .lift(
                flowableApiError()
            ).map {
                it.data
            }
    }

    override fun getDynamicFormCultivatorProfileData(
        borrowerId: String,
        schemeName: String
    ): Flowable<Map<String, FormFieldSchemaItem>> {
        return webService.getDynamicFormCultivatorProfileData(borrowerId, schemeName)
            .lift(flowableApiError()).map {
                it.data
            }
    }

    override fun submitLoan(applyLoanBodyPost: ApplyLoanBodyPost): Flowable<JsonElement> {
        return webService.applyLoan(applyLoanBodyPost).map { return@map it.body()?.data }
    }

    override fun checkIfHasLoan(borrowerId: String): Flowable<HasLoanDataItem> {
        return webService.checkIfHasLoan(borrowerId).lift(flowableApiError()).map {
            it.data
        }
    }
}
