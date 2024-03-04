package com.agree.ecosystem.partnership.presentation.menu.registration

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationBodyPost
import com.agree.ecosystem.partnership.domain.reqres.PartnershipUsecase
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.domain.reqres.model.unittype.UnitType
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class RegistrationViewModel(
    private val usecase: PartnershipUsecase
) : DevViewModel() {

    private val _unitType = DevData<List<UnitType>>().apply { default() }
    val unitType = _unitType.immutable()

    private val _registration = DevData<RegistrationStatusDetails>().apply { default() }
    val registration = _registration.immutable()

    private val _validation = DevData<Validation>().apply { default() }
    val validation = _validation.immutable()

    val partnershipType = MutableLiveData<String>()
    fun setPartnershipType(text: String) {
        partnershipType.value = text
    }

    val commodity = MutableLiveData<String>()
    fun setCommodity(text: String) {
        commodity.value = text
    }

    val address = MutableLiveData<String>()
    fun setAddress(text: String) {
        address.value = text
    }

    val province = MutableLiveData<String>()
    fun setProvince(text: String) {
        province.value = text
    }

    val provinceId = MutableLiveData<String>()
    fun setProvinceId(text: String) {
        provinceId.value = text
    }

    val districtId = MutableLiveData<String>()
    fun setDistrictId(text: String) {
        districtId.value = text
    }

    val districtName = MutableLiveData<String>()
    fun setDistrictName(text: String) {
        districtName.value = text
    }

    val subDistrictId = MutableLiveData<String>()
    fun setSubDistrictId(text: String) {
        subDistrictId.value = text
    }

    val subDistrictName = MutableLiveData<String>()
    fun setSubDistrictName(text: String) {
        subDistrictName.value = text
    }

    val villageId = MutableLiveData<String>()
    fun setVillageId(text: String) {
        villageId.value = text
    }

    val villageName = MutableLiveData<String>()
    fun setVillageName(text: String) {
        villageName.value = text
    }

    val unitTypeText = MutableLiveData<String>()
    fun setUnitType(text: String) {
        unitTypeText.value = text
    }

    val checkTerm = MutableLiveData<Boolean>()
    fun setCheckTerm(check: Boolean) {
        checkTerm.value = check
    }

    fun clearAddress() {
        address.value = ""
        province.value = ""
        provinceId.value = ""
        districtName.value = ""
        districtId.value = ""
        subDistrictName.value = ""
        subDistrictId.value = ""
        villageId.value = ""
        villageName.value = ""
    }

    fun getUnitType() {
        usecase.getUnitType()
            .setHandler(_unitType)
            .let { return@let disposable::add }
    }

    fun registrationPartnership(
        body: RegistrationBodyPost
    ) {
        usecase.registrationPartnership(body)
            .setHandler(_registration)
            .let { return@let disposable::add }
    }

    fun companyValidation(
        companyId: String
    ) {
        usecase.getValidationSubSector(companyId)
            .setHandler(_validation)
            .let { return@let disposable::add }
    }
}
