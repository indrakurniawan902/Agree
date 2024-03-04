package com.agree.ecosystem.partnership.domain.reqres

import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.data.reqres.model.company.CompanyParams
import com.agree.ecosystem.partnership.data.reqres.model.companymember.CompanyMemberParams
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatus.RegistrationStatusQuery
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMember
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMemberDetail
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.FinalAssessmentStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusTracking
import com.agree.ecosystem.partnership.domain.reqres.model.unittype.UnitType
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.agree.ecosystem.partnership.domain.reqres.model.vessel.Vessel
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import io.reactivex.Flowable

interface PartnershipUsecase {
    fun getRegistrationStatusList(query: RegistrationStatusQuery, page: Int, quantity: Int): Flowable<List<RegistrationStatus>>
    fun getRegistrationStatusDetails(submissionId: String): Flowable<RegistrationStatusDetails>
    fun getRegistrationStatusTracking(submissionId: String): Flowable<List<RegistrationStatusTracking>>
    fun cancelRegistration(submissionId: String): Flowable<Any>
    fun getDetailFinalAssessment(submissionId: String): Flowable<List<FinalAssessmentStatus>>

    fun getCompanyList(params: CompanyParams): Flowable<List<Company>>
    fun getDetailCompany(companyId: String): Flowable<Company>

    fun getUnitType(): Flowable<List<UnitType>>
    fun registrationPartnership(
        body: RegistrationBodyPost
    ): Flowable<RegistrationStatusDetails>

    fun getListSubVessel(vesselId: String, page: Int, quantity: Int): Flowable<List<SubVessel>>

    fun getSubSectors(): Flowable<List<SubSector>>

    fun getCompanyMembers(params: CompanyMemberParams): Flowable<List<CompanyMember>>
    fun getCompanyMemberDetails(companyMemberId: String): Flowable<CompanyMemberDetail>

    fun getVessels(id: String, page: Int, quantity: Int): Flowable<List<Vessel>>

    fun getSubSectorList(companyId: String, subSectorId: String): Flowable<List<Commodity>>

    fun getValidationSubSector(companyId: String): Flowable<Validation>
}
