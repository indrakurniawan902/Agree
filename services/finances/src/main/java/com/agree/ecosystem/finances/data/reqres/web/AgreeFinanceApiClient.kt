package com.agree.ecosystem.finances.data.reqres.web

import com.agree.ecosystem.finances.data.reqres.model.*
import com.devbase.data.source.web.model.DevApiResponse
import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AgreeFinanceApiClient {
    @GET("api/v1/modal/loan-packages")
    fun fetchListLoanPackage(
        @Query("source") source: String
    ): Flowable<Response<DevApiResponse<List<LoanPackageItem>>>>

    @GET("api/v1/modal/loan-packages/{loanPackageId}")
    fun fetchDetailLoanPackage(
        @Path("loanPackageId") loanPackageId: String
    ): Flowable<Response<DevApiResponse<DetaiLoanPackageItem>>>


    @GET("/api/v1/modal/borrower/proxy-farmer-list/superapps/{userId}")
    fun fetchListFarmerPartnership(
        @Path("userId") userId: String
    ): Flowable<Response<DevApiResponse<List<CultivatorBorrowerItem>>>>

    @GET("/api/v1/modal/loans-history-mobile/{userId}")
    fun fetchListActiveLoan(
        @Path("userId") userId: String
    ): Flowable<Response<DevApiResponse<List<MyLoanDataItem>>>>


    @GET("/api/v1/modal/borrower/loan-detail-borrower/{borrowerId}")
    fun fetchDetailBorrower(
        @Path("borrowerId") borrowerId: String
    ): Flowable<Response<DevApiResponse<ProfileFarmerItem>>>

    @GET("/api/v1/modal/borrower/proxy-farmer-list/{mitraId}/{loanPackageId}")
    fun checkMemberEligibility(
        @Path("mitraId") mitraId: String?,
        @Path("loanPackageId") loanPackageId: String
    ): Flowable<Response<DevApiResponse<List<ApplyLoanCultivatorItem>>>>

    @GET("/api/v1/modal/borrower/{borrowerId}/{schemeName}")
    fun getDynamicFormCultivatorProfileData(
        @Path("borrowerId") borrowerId: String,
        @Path("schemeName") schemeName: String,
    ): Flowable<Response<DevApiResponse<Map<String, FormFieldSchemaItem>>>>

    @POST("v1/modal/loans-group")
    fun applyLoan(
        @Body applyLoanBodyPost: ApplyLoanBodyPost
    ): Flowable<Response<DevApiResponse<JsonElement>>>

    @GET("/api/v1/modal/borrower/{borrowerId}/check-requested-loan")
    fun checkIfHasLoan(
        @Path("borrowerId") borrowerId: String
    ): Flowable<Response<DevApiResponse<HasLoanDataItem>>>

}
