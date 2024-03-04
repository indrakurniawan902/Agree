package com.agree.ecosystem.partnership.presentation.menu.registration

import android.view.View
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.CoreUtilsString
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationInfoBodyPost
import com.agree.ecosystem.partnership.databinding.FragmentRegistrationBinding
import com.agree.ecosystem.partnership.domain.reqres.model.unittype.UnitType
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.agree.ecosystem.partnership.presentation.navigation.RegistrationNavigation
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.menu.zone.district.SelectDistrictBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.province.SelectProvinceBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict.SelectSubDistrictBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.village.SelectVillageBottomSheet
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.Locale

class RegistrationFragment :
    AgrFormFragment<FragmentRegistrationBinding>(),
    RegistrationDataContract {

    private val unitValidation by validation { etUnit }
    private val args: RegistrationFragmentArgs by navArgs()
    private var unit = arrayListOf<UnitType>()
    private var dataSubSector = listOf<SubSector>()
    private val addressValidation by validation { etAddress }
    private val provinceValidation by validation { etProvince }
    private val districtValidation by validation { etDistrict }
    private val subDistrictValidation by validation { etSubDistrict }
    private val villageValidation by validation { etVillage }
    private var provinceId: String? = null
    private var districtId: String? = null
    private var subDistrictId: String? = null
    private var villageId: String? = null
    private var isClicked = false
    private var validate = true

    companion object {
        private const val PROVINCE = "province"
        private const val DISTRICT = "district"
        private const val SUB_DISTRICT = "subdistrict"
    }

    private fun dummySubSector(): List<SubSector> {
        val data = arrayListOf<SubSector>()
        args.data.subSectors.map {
            val commodities = arrayListOf<Commodity>()
            data.add(
                SubSector(
                    name = "${it.sectorName} ${it.subSectorName}",
                    id = it.subSectorId,
                    commodities = commodities,
                    status = "true",
                )
            )
        }
        return data
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            etUnit.fragmentManager = childFragmentManager
            rvSubSector.adapter = adapter
            adapter.apply {
                setEndlessScroll(rvSubSector)
                resetEndlessScroll()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(RegistrationObserver(this, viewModel))
        validation.registerValidations(
            unitValidation,
            addressValidation,
            provinceValidation,
            districtValidation,
            subDistrictValidation,
            villageValidation
        )
        viewModel.getUnitType()
    }

    override fun onAllFormValidated() {
        with(binding) {
            // Do Nothing
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            if (view is LgnTextField && isClicked) {
                view.error = errorMessage
            }
        }
    }

    override fun onFormValidated(view: View) {
        if (view is LgnTextField) view.error = ""
    }

    override fun isValid(): Boolean {
        with(binding) {
            var isAllValid = true
            if (!unitValidation.value.isValid) onFormUnvalidated(
                unitValidation.value.view,
                if (unitValidation.value.errorMessage == "") getString(
                    R.string.empty_wide
                ) else unitValidation.value.errorMessage
            )
            isAllValid = unitValidation.value.isValid && isAllValid

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
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.navController = navigation.getNavController()
            etProvince.setOnClickListener {
                SelectProvinceBottomSheet(onProvinceSelected = {
                    provinceId = it.id
                    etProvince.text = it.name
                    onSelectedZoneChanged(PROVINCE)
                    isClicked = false
                }).show(childFragmentManager, SelectProvinceBottomSheet.TAG)
            }
            etDistrict.setOnClickListener {
                provinceId?.let {
                    SelectDistrictBottomSheet(provinceId = it, onDistrictSelected = { district ->
                        districtId = district.id
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
                            subDistrictId = subDistrict.id
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
                        villageId = village.id
                        etVillage.text = village.name
                        isClicked = false
                    }).show(childFragmentManager, SelectVillageBottomSheet.TAG)
                }
            }
            btnRegister.setOnClickListener {
                isClicked = true
                var isAny = false
                dataSubSector.map { d ->
                    if (d.commodities.isNotEmpty()) isAny = true
                }

                if (!isAny) {
                    RegistrationBottomSheet("EmptyCommodity").showNow(
                        requireActivity().supportFragmentManager,
                        "dialog"
                    )
                } else {
                    if (isValid()) {
                        var size = etUnit.text.toDouble()
                        val unit = etUnit.unit.split(" ")[0].lowercase(Locale.getDefault())
                        if (unit == "m2") {
                            size = etUnit.text.toDouble() / 10000.0
                        }
                        val body = RegistrationInfoBodyPost(
                            size,
                            dataSubSector,
                            "mitra",
                            etAddress.text,
                            provinceId.toString(),
                            etProvince.text,
                            districtId.toString(),
                            etDistrict.text,
                            unit,
                            subDistrictId.toString(),
                            etSubDistrict.text,
                            villageId.toString(),
                            etVillage.text,
                            args.data.id,
                            args.data.name
                        )
                        RegistrationInfoBottomSheet(body).showNow(
                            requireActivity().supportFragmentManager,
                            "dialog"
                        )
                    }
                }
            }
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

    override fun initData() {
        super.initData()
        with(binding) {
            dataSubSector = dummySubSector()
            adapter = SubSectorAdapter(requireActivity(), onSubSectorAdapter = {
                var pickedCommodities = arrayListOf<Commodity>()
                args.dataCommodity.map { d ->
                    if (it.id == d.subSectorId) {
                        pickedCommodities.add(Commodity(d.subSectorId, d.name))
                    }
                }
                SubSectorBottomSheet(
                    pickedCommodities, it, onDataChecked = { checked ->
                        val commodities = arrayListOf<Commodity>()
                        if (checked.isNotEmpty()) {
                            checked.split(",").map { c ->
                                args.data.commodities.map { cm ->
                                    if (c.trim() == cm.name) {
                                        commodities.add(Commodity(cm.id, c))
                                    }
                                }
                            }
                        }
                        val subSector = arrayListOf<SubSector>()
                        subSector.addAll(dataSubSector)
                        subSector.map { d ->
                            if (it.name == d.name) {
                                d.commodities = commodities
                            }
                        }
                        adapter.clear()
                        adapter.addAll(subSector)
                    }
                ).showNow(requireActivity().supportFragmentManager, "dialog")
            })
            adapter.clear()
            adapter.addAll(dummySubSector())
        }
    }

    override fun initForm() {
        with(binding) {

            etUnit.addRule(
                unitValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(
                            R.string.label_wide_area
                        )
                    )
                )
            )
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

    override fun onGetUnitTypeLoading() {
        // Do Nothing
    }

    override fun onGetUnitTypeSuccess(data: List<UnitType>) {
        with(binding) {
            val array = arrayListOf<String>()
            data.map {
                array.add(it.name)
            }
            unit.addAll(data)
            etUnit.addAll(
                array.toList()
            )
            if (viewModel.unitTypeText.value != null) {
                etUnit.unit = viewModel.unitTypeText.value.toString()
            } else {
                etUnit.unit = array[0]
            }
        }
    }

    override fun onGetUnitTypeEmpty() {
        // Do Nothing
    }

    override fun onGetUnitTypeFailed(e: Throwable?) {
        // Do Nothing
    }

    override fun onValidationLoading() {
        // Do Nothing
    }

    override fun onValidationSuccess(data: Validation) {
        val dataTemp = arrayListOf<SubSector>()
        dataSubSector.map {
            data.subsectors.map { d ->
                if (d.id == it.id) {
                    dataTemp.add(
                        SubSector(
                            name = it.name,
                            id = d.id,
                            commodities = it.commodities,
                            status = d.status
                        )
                    )
                }
            }
        }
        if (validate) {
            dataSubSector = dataTemp
            adapter.clear()
            adapter.addAll(dataSubSector)
            validate = false
        }
    }

    override fun onValidationEmpty() {
        // Do Nothing
    }

    override fun onResume() {
        super.onResume()
        initForm()
    }

    private lateinit var adapter: SubSectorAdapter
    private val navigation: RegistrationNavigation by navigation { activity }
    private val viewModel: RegistrationViewModel by sharedViewModel()
}
