package com.agree.ecosystem.partnership.presentation.menu.addaddress

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.agree.ecosystem.core.utils.CoreUtilsString
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.FragmentAddAddressBinding
import com.agree.ecosystem.partnership.presentation.menu.registration.RegistrationViewModel
import com.agree.ecosystem.partnership.presentation.navigation.RegistrationNavigation
import com.agree.ecosystem.utilities.presentation.menu.zone.district.SelectDistrictBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.province.SelectProvinceBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict.SelectSubDistrictBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.village.SelectVillageBottomSheet
import com.agree.ui.UiKitDrawable
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddAddressFragment : AgrFormFragment<FragmentAddAddressBinding>() {

    private val addressValidation by validation { etAddress }
    private val provinceValidation by validation { etProvince }
    private val districtValidation by validation { etDistrict }
    private val subDistrictValidation by validation { etSubDistrict }
    private val villageValidation by validation { etVillage }

    //    private val poultryNav: RegistrationPartnershipUsecase by navigation { activity }
    private val navigation: RegistrationNavigation by navigation { activity }
    private var provinceId: String? = null
    private var districtId: String? = null
    private var subDistrictId: String? = null
    private var villageId: String? = null
    private var isClicked = false

    companion object {
        private const val PROVINCE = "province"
        private const val DISTRICT = "district"
        private const val SUB_DISTRICT = "subdistrict"
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), UiKitDrawable.ic_close)
            toolbar.navController = navigation.getNavController()
        }
    }

    override fun initData() {
        super.initData()
        with(binding) {
            viewModel.address.observe(
                viewLifecycleOwner,
                Observer {
                    if (it != "") {
                        etAddress.text = it
                    }
                }
            )
            viewModel.province.observe(
                viewLifecycleOwner,
                Observer {
                    if (it != "") {
                        etProvince.text = it
                    }
                }
            )
            viewModel.districtName.observe(
                viewLifecycleOwner,
                Observer {
                    if (it != "") {
                        etDistrict.text = it
                    }
                }
            )
            viewModel.subDistrictName.observe(
                viewLifecycleOwner,
                Observer {
                    if (it != "") {
                        etSubDistrict.text = it
                    }
                }
            )
            viewModel.villageName.observe(
                viewLifecycleOwner,
                Observer {
                    if (it != "") {
                        etVillage.text = it
                    }
                }
            )
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            etProvince.setOnClickListener {
                SelectProvinceBottomSheet(onProvinceSelected = {
                    provinceId = it.provinceId
                    etProvince.text = it.name
                    onSelectedZoneChanged(PROVINCE)
                    isClicked = false
                }).show(childFragmentManager, SelectProvinceBottomSheet.TAG)
            }
            etDistrict.setOnClickListener {
                provinceId?.let {
                    SelectDistrictBottomSheet(provinceId = it, onDistrictSelected = { district ->
                        districtId = district.districtId
                        etDistrict.text = district.name
                        onSelectedZoneChanged(DISTRICT)
                        isClicked = false
                    }).show(childFragmentManager, SelectDistrictBottomSheet.TAG)
                }
            }
            etSubDistrict.setOnClickListener {
                districtId?.let {
                    SelectSubDistrictBottomSheet(
                        districtId = it,
                        onSubDistrictSelected = { subDistrict ->
                            subDistrictId = subDistrict.subdistrictId
                            etSubDistrict.text = subDistrict.name
                            onSelectedZoneChanged(SUB_DISTRICT)
                            isClicked = false
                        }
                    ).show(childFragmentManager, SelectSubDistrictBottomSheet.TAG)
                }
            }
            etVillage.setOnClickListener {
                subDistrictId?.let {
                    SelectVillageBottomSheet(subDistrictId = it, onVillageSelected = { village ->
                        villageId = village.villageId
                        etVillage.text = village.name
                        isClicked = false
                    }).show(childFragmentManager, SelectVillageBottomSheet.TAG)
                }
            }
            btnSave.setOnClickListener {
                isClicked = true
                if (isValid()) {
                    viewModel.setAddress(etAddress.text)
                    if (provinceId != null) viewModel.setProvinceId(provinceId.toString())
                    viewModel.setProvince(etProvince.text)
                    if (districtId != null) viewModel.setDistrictId(districtId.toString())
                    viewModel.setDistrictName(etDistrict.text)
                    if (subDistrictId != null) viewModel.setSubDistrictId(subDistrictId.toString())
                    viewModel.setSubDistrictName(etSubDistrict.text)
                    if (villageId != null) viewModel.setVillageId(villageId.toString())
                    viewModel.setVillageName(etVillage.text)
                    if (isAddressValid()) {
                        activity?.onBackPressed()
                    }
                }
            }
        }
    }

    private fun isAddressValid(): Boolean {
        var isAllValid = true
        isAllValid = isAllValid && viewModel.provinceId.value != null
        isAllValid = isAllValid && viewModel.districtId.value != null
        isAllValid = isAllValid && viewModel.subDistrictId.value != null
        isAllValid = isAllValid && viewModel.villageId.value != null
        return isAllValid
    }

    override fun isValid(): Boolean {
        var isAllValid = true

        if (!addressValidation.value.isValid) onFormUnvalidated(
            addressValidation.value.view,
            addressValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_address))
            }
        )
        isAllValid = provinceValidation.value.isValid && isAllValid

        if (!provinceValidation.value.isValid) onFormUnvalidated(
            provinceValidation.value.view,
            provinceValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_province)
            }
        )
        isAllValid = provinceValidation.value.isValid && isAllValid
        if (!districtValidation.value.isValid) onFormUnvalidated(
            districtValidation.value.view,
            districtValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_district)
            }
        )
        isAllValid = districtValidation.value.isValid && isAllValid
        if (!subDistrictValidation.value.isValid) onFormUnvalidated(
            subDistrictValidation.value.view,
            subDistrictValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_sub_district)
            }
        )
        isAllValid = subDistrictValidation.value.isValid && isAllValid
        if (!villageValidation.value.isValid) onFormUnvalidated(
            villageValidation.value.view,
            villageValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_village)
            }
        )
        isAllValid = villageValidation.value.isValid && isAllValid
        return isAllValid
    }

    override fun initObserver() {
        super.initObserver()
        validation.registerValidations(
            addressValidation,
            provinceValidation,
            districtValidation,
            subDistrictValidation,
            villageValidation
        )
    }

    override fun initForm() {
        with(binding) {
            etAddress.addRule(
                addressValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(
                            R.string.label_address
                        )
                    )
                )
            )
            etProvince.addRule(
                provinceValidation,
                notEmptyRule(getStringResource(CoreUtilsString.error_rule_province))
            )
            etDistrict.addRule(
                districtValidation,
                notEmptyRule(getStringResource(CoreUtilsString.error_rule_district))
            )
            etSubDistrict.addRule(
                subDistrictValidation,
                notEmptyRule(getStringResource(CoreUtilsString.error_rule_sub_district))
            )
            etVillage.addRule(
                villageValidation,
                notEmptyRule(getStringResource(CoreUtilsString.error_rule_village))
            )
        }
    }

    fun onSelectedZoneChanged(zoneType: String) {
        with(binding) {
            when (zoneType) {
                PROVINCE -> {
                    districtId = null
                    etDistrict.text = ""
                    onSelectedZoneChanged(DISTRICT)
                }
                DISTRICT -> {
                    subDistrictId = null
                    etSubDistrict.text = ""
                    onSelectedZoneChanged(SUB_DISTRICT)
                }
                SUB_DISTRICT -> {
                    villageId = null
                    etVillage.text = ""
                }
            }
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnSave.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            if (view is LgnTextField && isClicked) {
                view.error = errorMessage
                btnSave.isEnable = true
            }
        }
    }

    override fun onFormValidated(view: View) {
        with(binding) {
            if (view is LgnTextField) view.error = ""
        }
    }

    private val viewModel: RegistrationViewModel by sharedViewModel()
}
