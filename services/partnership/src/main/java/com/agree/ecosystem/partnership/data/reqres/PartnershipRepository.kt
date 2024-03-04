package com.agree.ecosystem.partnership.data.reqres

import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
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
import com.agree.ecosystem.utilities.data.reqres.model.commodity.CommodityItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorItem
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface PartnershipRepository : DevRepository {
    fun getRegistrationStatusList(
        query: RegistrationStatusQuery,
        page: Int,
        quantity: Int
    ): Flowable<List<RegistrationStatusItem>>

    fun getRegistrationStatusDetails(submissionId: String): Flowable<RegistrationStatusDetailsItem>
    fun getRegistrationStatusTracking(submissionId: String): Flowable<List<RegistrationStatusTrackingItem>>
    fun cancelRegistration(submissionId: String): Flowable<Any>
    fun getDetailFinalAssessment(submissionId: String): Flowable<List<FinalAssessmentStatusItem>>

    fun getCompanyList(params: CompanyParams): Flowable<List<CompanyItem>>
    fun getDetailCompany(companyId: String): Flowable<CompanyItem>

    fun getUnitType(): Flowable<List<UnitTypeItem>>

    fun registrationPartnership(
        body: RegistrationBodyPost
    ): Flowable<RegistrationStatusDetailsItem>

    fun getListSubVessel(vesselId: String, page: Int, quantity: Int): Flowable<List<SubVesselItem>>

    fun getSubSectors(): Flowable<List<SubSectorItem>>

    fun getCompanyMembers(params: CompanyMemberParams): Flowable<List<CompanyMemberItem>>
    fun getCompanyMemberDetails(companyMemberId: String): Flowable<CompanyMemberDetailItem>

    fun getVessels(id: String, page: Int, quantity: Int): Flowable<List<VesselItem>>

    fun getSubSectorList(companyId: String, subSectorId: String): Flowable<List<CommodityItem>>

    fun getValidationSubSector(companyId: String): Flowable<ValidationItem>
}
