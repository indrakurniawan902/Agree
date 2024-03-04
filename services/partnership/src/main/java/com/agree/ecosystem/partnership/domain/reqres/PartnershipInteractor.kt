package com.agree.ecosystem.partnership.domain.reqres

import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.data.reqres.PartnershipRepository
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

class PartnershipInteractor(
    private val repo: PartnershipRepository
) : PartnershipUsecase {
    override fun getRegistrationStatusList(
        query: RegistrationStatusQuery,
        page: Int,
        quantity: Int
    ): Flowable<List<RegistrationStatus>> {
        return repo.getRegistrationStatusList(query, page, quantity).map {
            it.map { registrationStatusItem ->
                registrationStatusItem.toRegistrationStatus()
            }
        }
    }

    override fun getCompanyList(params: CompanyParams): Flowable<List<Company>> {
        return repo.getCompanyList(params).map { it.map { data -> data.toCompany() } }
    }

    override fun getRegistrationStatusDetails(submissionId: String): Flowable<RegistrationStatusDetails> {
        return repo.getRegistrationStatusDetails(submissionId).map {
            it.toRegistrationStatusDetails()
        }
    }

    override fun getRegistrationStatusTracking(submissionId: String): Flowable<List<RegistrationStatusTracking>> {
        return repo.getRegistrationStatusTracking(submissionId).map {
            it.map { data -> data.toRegistrationStatusTracking() }
        }
    }

    override fun cancelRegistration(submissionId: String): Flowable<Any> {
        return repo.cancelRegistration(submissionId)
    }

    override fun getDetailFinalAssessment(submissionId: String): Flowable<List<FinalAssessmentStatus>> {
        return repo.getDetailFinalAssessment(submissionId).map { it.map { data -> data.toFinalAssessmentStatus() } }
    }

    override fun getDetailCompany(companyId: String): Flowable<Company> {
        return repo.getDetailCompany(companyId).map {
            it.toCompany()
        }
    }

    override fun getUnitType(): Flowable<List<UnitType>> {
        return repo.getUnitType().map {
            it.map { data ->
                data.toUnitType()
            }
        }
    }

    override fun registrationPartnership(
        body: RegistrationBodyPost
    ): Flowable<RegistrationStatusDetails> {
        return repo.registrationPartnership(body)
            .map { it.toRegistrationStatusDetails() }
    }

    override fun getListSubVessel(
        vesselId: String,
        page: Int,
        quantity: Int
    ): Flowable<List<SubVessel>> {
        return repo.getListSubVessel(vesselId, page, quantity).map {
            it.map { subVessel ->
                subVessel.toSubVessel()
            }
        }
    }

    override fun getSubSectors(): Flowable<List<SubSector>> {
        return repo.getSubSectors().map {
            it.map { subSectorItem ->
                subSectorItem.toSubSector()
            }
        }
    }

    override fun getCompanyMembers(params: CompanyMemberParams): Flowable<List<CompanyMember>> {
        return repo.getCompanyMembers(params).map {
            it.map { company ->
                company.toCompanyMember()
            }
        }
    }

    override fun getCompanyMemberDetails(companyMemberId: String): Flowable<CompanyMemberDetail> {
        return repo.getCompanyMemberDetails(companyMemberId).map { it.toCompanyMember() }
    }

    override fun getVessels(id: String, page: Int, quantity: Int): Flowable<List<Vessel>> {
        return repo.getVessels(id, page, quantity).map {
            it.map { vessel ->
                vessel.toVessel()
            }
        }
    }

    override fun getSubSectorList(
        companyId: String,
        subSectorId: String
    ): Flowable<List<Commodity>> {
        return repo.getSubSectorList(companyId, subSectorId).map {
            it.map { data -> data.toCommodity() }
        }
    }

    override fun getValidationSubSector(companyId: String): Flowable<Validation> {
        return repo.getValidationSubSector(companyId).map {
            it.toValidation()
        }
    }
}
