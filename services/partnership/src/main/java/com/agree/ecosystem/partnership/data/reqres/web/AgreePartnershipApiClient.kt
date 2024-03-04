package com.agree.ecosystem.partnership.data.reqres.web

import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.partnership.data.reqres.model.cancelregistration.CancelRegistrationBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.company.CompanyItem
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberDetailItem
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberItem
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatus.RegistrationStatusItem
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails.FinalAssessmentStatusItem
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails.RegistrationStatusDetailsItem
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails.RegistrationStatusTrackingItem
import com.agree.ecosystem.partnership.data.reqres.model.unittype.UnitTypeItem
import com.agree.ecosystem.partnership.data.reqres.model.validation.ValidationItem
import com.agree.ecosystem.partnership.data.reqres.model.vessel.VesselItem
import com.agree.ecosystem.utilities.data.reqres.model.commodity.CommodityItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.*

interface AgreePartnershipApiClient {
    @GET("partners/v1/submissions")
    fun getRegistrationStatusList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("quantity") quantity: Int
    ): Flowable<Response<DevApiResponse<List<RegistrationStatusItem>>>>

    @GET("partners/v1/companies")
    fun getCompanyList(
        @Query("page") page: Int,
        @Query("quantity") quantity: Int,
        @Query("subsector_id") subSectorId: String = "",
        @Query("user_id") userId: String? = null,
        @Query("search") search: String = ""
    ): Flowable<Response<DevApiResponse<List<CompanyItem>>>>

    @GET("partners/v1/submissions/{id}")
    fun getRegistrationStatusDetails(
        @Path("id") submissionId: String,
        @Query("group_commodity") groupCommodity: String = "subsector"
    ): Flowable<Response<DevApiResponse<RegistrationStatusDetailsItem>>>

    @GET("partners/v1/companies/{id}")
    fun getDetailCompany(
        @Path("id") companyId: String
    ): Flowable<Response<DevApiResponse<CompanyItem>>>

    @GET("partners/v1/submissions/status/{id}")
    fun getRegistrationStatusTracking(
        @Path("id") submissionId: String
    ): Flowable<Response<DevApiResponse<List<RegistrationStatusTrackingItem>>>>

    @GET("partners/v1/submissions/subvessel/{submission_id}")
    fun getDetailFinalAssessment(
        @Path("submission_id") submissionId: String,
    ): Flowable<Response<DevApiResponse<List<FinalAssessmentStatusItem>>>>

    @GET("utilities/v1/utilities")
    fun getUnitType(
        @Query("utility_type") utilityType: String = "unit"
    ): Flowable<Response<DevApiResponse<List<UnitTypeItem>>>>

    @POST("partners/v1/submissions")
    fun registerPartnership(
        @Body data: RegistrationBodyPost
    ): Flowable<Response<DevApiResponse<RegistrationStatusDetailsItem>>>

    @GET("activities/v1/subvessels")
    fun getListSubVessel(
        @Query("vessel_id") vesselId: String,
        @Query("page") page: Int,
        @Query("quantity") quantity: Int
    ): Flowable<Response<DevApiResponse<List<SubVesselItem>>>>

    @PUT("partners/v1/submissions/{id}")
    fun cancelRegistration(
        @Path("id") submissionId: String,
        @Body data: CancelRegistrationBodyPost
    ): Flowable<Response<DevApiResponse<Any>>>

    @GET("utilities/v1/subsectors")
    fun getSubSectors(): Flowable<Response<DevApiResponse<List<SubSectorItem>>>>

    @GET("partners/v1/company-member")
    fun getCompanyMembers(
        @Query("page") page: Int,
        @Query("quantity") quantity: Int,
        @Query("subsector_id") subSectorIds: String?,
        @Query("user_id") userId: String
    ): Flowable<Response<DevApiResponse<List<CompanyMemberItem>>>>

    @GET("partners/v1/company-member/{id}")
    fun getCompanyMemberDetail(
        @Path("id") companyMemberId: String,
        @Query("commodity_group") commodityGroup: String = "sub_sector"
    ): Flowable<Response<DevApiResponse<CompanyMemberDetailItem>>>

    @GET("activities/v1/vessels")
    fun getVessels(
        @Query("company_member_id") companyMemberId: String,
        @Query("page") page: Int,
        @Query("quantity") quantity: Int,
    ): Flowable<Response<DevApiResponse<List<VesselItem>>>>

    @GET("partners/v1/companies/commodities/{company_id}")
    fun getSubSectorList(
        @Path("company_id") companyId: String,
        @Query("subsector_id") subSectorId: String
    ): Flowable<Response<DevApiResponse<List<CommodityItem>>>>

    @GET("partners/v1/submissions/validation")
    fun getValidationSubSector(
        @Query("company_id") companyId: String,
    ): Flowable<Response<DevApiResponse<ValidationItem>>>
}
