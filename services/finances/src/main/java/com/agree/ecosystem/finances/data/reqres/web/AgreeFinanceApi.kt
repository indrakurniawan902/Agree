package com.agree.ecosystem.finances.data.reqres.web

import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanBodyPost
import com.agree.ecosystem.finances.data.reqres.model.CultivatorBorrowerItem
import com.agree.ecosystem.finances.data.reqres.model.FormFieldSchemaItem
import com.agree.ecosystem.finances.data.reqres.model.LoanPackageItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.Response

class AgreeFinanceApi(
    private val api: AgreeFinanceApiClient
) : AgreeFinanceApiClient, WebService {
    override fun fetchListLoanPackage(source: String): Flowable<Response<DevApiResponse<List<LoanPackageItem>>>> {
        return api.fetchListLoanPackage(source)
    }

    override fun fetchDetailLoanPackage(loanPackageId: String) =
        api.fetchDetailLoanPackage(loanPackageId)

    override fun fetchListFarmerPartnership(userId: String): Flowable<Response<DevApiResponse<List<CultivatorBorrowerItem>>>> {
        return api.fetchListFarmerPartnership(userId)
    }

    override fun fetchListActiveLoan(userId: String) = api.fetchListActiveLoan(userId)
    override fun fetchDetailBorrower(borrowerId: String) = api.fetchDetailBorrower(borrowerId)

    override fun checkMemberEligibility(
        mitraId: String?, loanPackageId: String
    ) = api.checkMemberEligibility(mitraId, loanPackageId)

    override fun getDynamicFormCultivatorProfileData(
        borrowerId: String,
        schemeName: String,
    ): Flowable<Response<DevApiResponse<Map<String, FormFieldSchemaItem>>>> {
        return api.getDynamicFormCultivatorProfileData(borrowerId, schemeName)
    }

    override fun applyLoan(applyLoanBodyPost: ApplyLoanBodyPost): Flowable<Response<DevApiResponse<JsonElement>>> {
        return api.applyLoan(applyLoanBodyPost)
    }

    override fun checkIfHasLoan(borrowerId: String) = api.checkIfHasLoan(borrowerId)
}
