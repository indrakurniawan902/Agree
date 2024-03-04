package com.agree.ecosystem.partnership.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.partnership.data.reqres.model.cancelregistration.CancelRegistrationBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.company.CompanyItem
import com.agree.ecosystem.partnership.data.reqres.model.company.CompanyParams
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberDetailItem
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberItem
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberParams
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatus.RegistrationStatusItem
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatus.RegistrationStatusQuery
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails.FinalAssessmentStatusItem
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails.RegistrationStatusDetailsItem
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails.RegistrationStatusTrackingItem
import com.agree.ecosystem.partnership.data.reqres.model.unittype.UnitTypeItem
import com.agree.ecosystem.partnership.data.reqres.model.validation.ValidationItem
import com.agree.ecosystem.partnership.data.reqres.model.vessel.VesselItem
import com.agree.ecosystem.partnership.data.reqres.web.AgreePartnershipApi
import com.agree.ecosystem.utilities.data.reqres.model.commodity.CommodityItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorItem
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.google.gson.Gson
import io.reactivex.Flowable

@WorkerThread
class PartnershipDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreePartnershipApi
) : PartnershipRepository {
    override fun getRegistrationStatusList(
        query: RegistrationStatusQuery,
        page: Int,
        quantity: Int
    ): Flowable<List<RegistrationStatusItem>> {
        val queryString = Gson().toJson(query)
        return webService.getRegistrationStatusList(queryString, page, quantity)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getCompanyList(params: CompanyParams): Flowable<List<CompanyItem>> {
        return webService.getCompanyList(
            page = params.page,
            quantity = params.quantity,
            subSectorId = params.subSectorId,
            search = params.search
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getRegistrationStatusDetails(submissionId: String): Flowable<RegistrationStatusDetailsItem> {
        return webService.getRegistrationStatusDetails(submissionId)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getRegistrationStatusTracking(submissionId: String): Flowable<List<RegistrationStatusTrackingItem>> {
        return webService.getRegistrationStatusTracking(submissionId)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun cancelRegistration(submissionId: String): Flowable<Any> {
        return webService.cancelRegistration(submissionId, CancelRegistrationBodyPost())
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getDetailFinalAssessment(submissionId: String): Flowable<List<FinalAssessmentStatusItem>> {
        return webService.getDetailFinalAssessment(submissionId)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getDetailCompany(companyId: String): Flowable<CompanyItem> {
        return webService.getDetailCompany(companyId)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getUnitType(): Flowable<List<UnitTypeItem>> {
        return webService.getUnitType()
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun registrationPartnership(
        body: RegistrationBodyPost
    ): Flowable<RegistrationStatusDetailsItem> {
        return webService.registerPartnership(
            RegistrationBodyPost(
                body.size, body.subsector, body.type,
                body.address, body.provinceId, body.districtId, body.subDistrictId,
                body.villageId, body.companyId, body.commodity
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun getListSubVessel(vesselId: String, page: Int, quantity: Int): Flowable<List<SubVesselItem>> {
        return webService.getListSubVessel(vesselId, page, quantity)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getSubSectors(): Flowable<List<SubSectorItem>> {
        return webService.getSubSectors()
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getCompanyMembers(params: CompanyMemberParams): Flowable<List<CompanyMemberItem>> {
        return webService.getCompanyMembers(
            page = params.page,
            quantity = params.quantity,
            subSectorIds = params.getSubSectorIds(),
            userId = params.userId
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getCompanyMemberDetails(companyMemberId: String): Flowable<CompanyMemberDetailItem> {
        return webService.getCompanyMemberDetail(
            companyMemberId = companyMemberId
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getVessels(id: String, page: Int, quantity: Int): Flowable<List<VesselItem>> {
        return webService.getVessels(
            id, page, quantity
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getSubSectorList(companyId: String, subSectorId: String): Flowable<List<CommodityItem>> {
        return webService.getSubSectorList(companyId, subSectorId).lift(flowableApiError()).map { it.data }
    }

    override fun getValidationSubSector(companyId: String): Flowable<ValidationItem> {
        return webService.getValidationSubSector(companyId).lift(flowableApiError()).map { it.data }
    }
}
