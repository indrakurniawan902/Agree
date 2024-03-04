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
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response

class AgreePartnershipApi(
    private val api: AgreePartnershipApiClient
) : AgreePartnershipApiClient, WebService {

    override fun getCompanyList(
        page: Int,
        quantity: Int,
        subSectorId: String,
        userId: String?,
        search: String
    ): Flowable<Response<DevApiResponse<List<CompanyItem>>>> {
        return api.getCompanyList(page, quantity, subSectorId, userId, search)
    }

    override fun getRegistrationStatusList(
        query: String,
        page: Int,
        quantity: Int
    ): Flowable<Response<DevApiResponse<List<RegistrationStatusItem>>>> {
        return api.getRegistrationStatusList(query, page, quantity)
    }

    override fun getRegistrationStatusDetails(
        submissionId: String,
        groupCommodity: String
    ): Flowable<Response<DevApiResponse<RegistrationStatusDetailsItem>>> {
        return api.getRegistrationStatusDetails(submissionId)
    }

    override fun getDetailCompany(companyId: String): Flowable<Response<DevApiResponse<CompanyItem>>> {
        return api.getDetailCompany(companyId)
    }

    override fun getUnitType(utilityType: String): Flowable<Response<DevApiResponse<List<UnitTypeItem>>>> {
        return api.getUnitType()
    }

    override fun registerPartnership(data: RegistrationBodyPost): Flowable<Response<DevApiResponse<RegistrationStatusDetailsItem>>> {
        return api.registerPartnership(data)
    }

    override fun getRegistrationStatusTracking(submissionId: String): Flowable<Response<DevApiResponse<List<RegistrationStatusTrackingItem>>>> {
        return api.getRegistrationStatusTracking(submissionId)
    }

    override fun getDetailFinalAssessment(submissionId: String): Flowable<Response<DevApiResponse<List<FinalAssessmentStatusItem>>>> {
        return api.getDetailFinalAssessment(submissionId)
    }

    override fun getListSubVessel(
        vesselId: String,
        page: Int,
        quantity: Int
    ): Flowable<Response<DevApiResponse<List<SubVesselItem>>>> {
        return api.getListSubVessel(vesselId, page, quantity)
    }

    override fun cancelRegistration(
        submissionId: String,
        data: CancelRegistrationBodyPost
    ): Flowable<Response<DevApiResponse<Any>>> {
        return api.cancelRegistration(submissionId, data)
    }

    override fun getSubSectors(): Flowable<Response<DevApiResponse<List<SubSectorItem>>>> {
        return api.getSubSectors()
    }

    override fun getCompanyMembers(
        page: Int,
        quantity: Int,
        subSectorIds: String?,
        userId: String
    ): Flowable<Response<DevApiResponse<List<CompanyMemberItem>>>> {
        return api.getCompanyMembers(page, quantity, subSectorIds, userId)
    }

    override fun getCompanyMemberDetail(companyMemberId: String, commodityGroup: String): Flowable<Response<DevApiResponse<CompanyMemberDetailItem>>> {
        return api.getCompanyMemberDetail(companyMemberId, commodityGroup)
    }

    override fun getVessels(
        companyMemberId: String,
        page: Int,
        quantity: Int
    ): Flowable<Response<DevApiResponse<List<VesselItem>>>> {
        return api.getVessels(companyMemberId, page, quantity)
    }

    override fun getSubSectorList(
        companyId: String,
        subSectorId: String
    ): Flowable<Response<DevApiResponse<List<CommodityItem>>>> {
        return api.getSubSectorList(companyId, subSectorId)
    }

    override fun getValidationSubSector(companyId: String): Flowable<Response<DevApiResponse<ValidationItem>>> {
        return api.getValidationSubSector(companyId)
    }
}
