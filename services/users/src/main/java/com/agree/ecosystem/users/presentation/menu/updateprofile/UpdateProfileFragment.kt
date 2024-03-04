package com.agree.ecosystem.users.presentation.menu.updateprofile

import android.view.View
import com.agree.ecosystem.core.utils.CoreUtilsString
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.defaultErrorHandling
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.checkByTag
import com.agree.ecosystem.core.utils.utility.extension.getCheckedTag
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.rules.AgrCheckableRule
import com.agree.ecosystem.core.utils.utility.validation.rules.EmailRule
import com.agree.ecosystem.core.utils.utility.validation.rules.mobileNumberOnlyRule
import com.agree.ecosystem.core.utils.utility.validation.update
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ecosystem.users.R
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.agree.ecosystem.users.databinding.FragmentUpdateProfileBinding
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.agree.ecosystem.users.presentation.navigation.MainNavigation
import com.agree.ecosystem.utilities.presentation.menu.zone.district.SelectDistrictBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.province.SelectProvinceBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict.SelectSubDistrictBottomSheet
import com.agree.ecosystem.utilities.presentation.menu.zone.village.SelectVillageBottomSheet
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.successSnackBar
import com.agree.ui.widget.photofield.PhotoFieldView
import com.devbase.presentation.validation.util.alphaOnlyRule
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.base.LgnTextField
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UpdateProfileFragment :
    AgrFormFragment<FragmentUpdateProfileBinding>(),
    UpdateProfileDataContract {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.setBackButtonOnClick { nav.goToPrevious() }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(UpdateProfileObserver(this, viewModel))
        validation.registerValidations(
            nameValidation,
            jobValidation,
            phoneValidation,
            emailValidation,
            addressValidation,
            provinceValidation,
            districtValidation,
            subDistrictValidation,
            villageValidation,
            genderValidation
        )
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            viewModel.getProfile(prefs.userId)
            etProvince.setOnClickListener {
                SelectProvinceBottomSheet(onProvinceSelected = {
                    provinceId = it.id
                    etProvince.text = it.name
                    onSelectedZoneChanged(PROVINCE)
                }).show(childFragmentManager, SelectProvinceBottomSheet.TAG)
            }
            etDistrict.setOnClickListener {
                provinceId?.let {
                    SelectDistrictBottomSheet(provinceId = it, onDistrictSelected = { district ->
                        districtId = district.id
                        etDistrict.text = district.name
                        onSelectedZoneChanged(DISTRICT)
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
                        }
                    ).show(childFragmentManager, SelectSubDistrictBottomSheet.TAG)
                }
            }
            etVillage.setOnClickListener {
                subDistrictId?.let {
                    SelectVillageBottomSheet(subDistrictId = it, onVillageSelected = { village ->
                        villageId = village.id
                        etVillage.text = village.name
                    }).show(childFragmentManager, SelectVillageBottomSheet.TAG)
                }
            }
            pfProfile.addUploadListener(object : PhotoFieldView.UploadListener {
                override fun onUploadPicture() {
                    // Do Nothing
                }

                override fun onFinish(file: File) {
                    viewModel.updateAvatar(prefs.userId, file)
                }
            })
            btnSave.setOnClickListener {
                if (isValid()) {
                    DialogUtils.showCustomDialog(
                        context = requireContext(),
                        title = "",
                        message = getString(R.string.label_update_profile_dialog),
                        positiveAction = Pair(getString(R.string.action_yes)) {
                            doUpdateProfile()
                        },
                        negativeAction = Pair(getString(R.string.action_cancel), null),
                        autoDismiss = false
                    )
                }
            }
        }
    }

    override fun initForm() {
        with(binding) {
            etFullname.addRule(
                nameValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_fullname)
                    )
                ),
                alphaOnlyRule(getStringResource(R.string.error_rule_fullname))
            )
            etJob.addRule(
                jobValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_job)
                    )
                ),
                alphaOnlyRule(getStringResource(R.string.error_rule_job))
            )
            etEmail.addRule(
                emailValidation,
                EmailRule(getStringResource(R.string.error_rule_email))
            )
            etPhoneNumber.addRule(
                phoneValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_phone_number)
                    )
                ),
                mobileNumberOnlyRule(getStringResource(R.string.error_rule_phone))
            )
            etAddress.addRule(
                addressValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(R.string.label_address)
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
            rcGender.addRule(
                genderValidation,
                AgrCheckableRule(getStringResource(CoreUtilsString.error_rule_gender))
            )
        }
    }

    override fun isValid(): Boolean {
        if (!nameValidation.value.isValid) onFormUnvalidated(
            nameValidation.value.view,
            nameValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_fullname))
            }
        )
        if (!jobValidation.value.isValid) onFormUnvalidated(
            jobValidation.value.view,
            jobValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_job))
            }
        )
        if (!phoneValidation.value.isValid) onFormUnvalidated(
            phoneValidation.value.view,
            phoneValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_phonenumber))
            }
        )
        if (!addressValidation.value.isValid) onFormUnvalidated(
            addressValidation.value.view,
            addressValidation.value.errorMessage.ifEmpty {
                getString(R.string.error_empty_field, getString(R.string.label_address))
            }
        )
        if (!provinceValidation.value.isValid) onFormUnvalidated(
            provinceValidation.value.view,
            provinceValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_province)
            }
        )
        if (!districtValidation.value.isValid) onFormUnvalidated(
            districtValidation.value.view,
            districtValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_district)
            }
        )
        if (!subDistrictValidation.value.isValid) onFormUnvalidated(
            subDistrictValidation.value.view,
            subDistrictValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_sub_district)
            }
        )
        if (!villageValidation.value.isValid) onFormUnvalidated(
            villageValidation.value.view,
            villageValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_village)
            }
        )
        if (!genderValidation.value.isValid) onFormUnvalidated(
            genderValidation.value.view,
            genderValidation.value.errorMessage.ifEmpty {
                getStringResource(CoreUtilsString.error_rule_gender)
            }
        )
        return nameValidation.value.isValid && phoneValidation.value.isValid &&
            jobValidation.value.isValid && emailValidation.value.isValid &&
            addressValidation.value.isValid && provinceValidation.value.isValid &&
            districtValidation.value.isValid && subDistrictValidation.value.isValid &&
            villageValidation.value.isValid && genderValidation.value.isValid
    }

    override fun onAllFormValidated() {
        // Do Nothing
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            if (view is LgnTextField) {
                view.error = errorMessage
            } else if (view is LgnRadioContainer) {
                view.error = errorMessage
            }
            btnSave.isLoading = false
        }
    }

    override fun onFormValidated(view: View) {
        if (view is LgnTextField) view.error = String()
        else if (view is LgnRadioContainer) view.error = String()
    }

    override fun onLoading() {
        binding.btnSave.isLoading = true
    }

    override fun onProfileLoading() {
        binding.msvUpdateProfile.showLoadingLayout()
    }

    override fun onUpdateAvatarLoading() {
        // Do Nothing
    }

    override fun onGetProfileSuccess(data: Profile) {
        with(binding) {
            msvUpdateProfile.showDefaultLayout()
            pfProfile.url(data.avatar)
            etFullname.text = data.name
            etJob.text = data.job
            etPhoneNumber.text = data.phoneNumber
            etEmail.text = data.email
            etAddress.text = data.address
            etProvince.text = data.provinceName
            etDistrict.text = data.districtName
            etSubDistrict.text = data.subDistrictName
            etVillage.text = data.villageName
            provinceId = data.provinceId
            districtId = data.districtId
            subDistrictId = data.subDistrictId
            villageId = data.villageId
            if (data.birthDate.isNotEmpty()) {
                birthDate = data.birthDate
            }
            rcGender.checkByTag(data.gender)
        }
    }

    override fun onGetProfileFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun doUpdateProfile() {
        with(binding) {
            viewModel.updateProfile(
                userId = prefs.userId,
                data = UpdateProfileBodyPost(
                    id = prefs.userId,
                    name = etFullname.text,
                    phoneNumber = etPhoneNumber.text,
                    email = etEmail.text,
                    address = etAddress.text,
                    provinceId = provinceId.orEmpty(),
                    districtId = districtId.orEmpty(),
                    subDistrictId = subDistrictId.orEmpty(),
                    villageId = villageId.orEmpty(),
                    gender = rcGender.getCheckedTag(),
                    birthDate = birthDate,
                    job = etJob.text
                )
            ) {
                when (this) {
                    409 -> phoneValidation.update(
                        etPhoneNumber, false, getString(R.string.error_phone_already_registered)
                    )

                    410 -> emailValidation.update(
                        etEmail, false, getString(R.string.error_email_already_register)
                    )

                    400 -> {
                        genderValidation.update(
                            rcGender, false, getString(CoreUtilsString.error_rule_gender)
                        )
                        btnSave.isLoading = false
                    }

                    else -> defaultErrorHandling(this, it.orEmpty()) {
                        doUpdateProfile()
                    }
                }
            }
        }
    }

    override fun onUpdateProfileSuccess(data: Profile) {
        nav.goToPrevious()
        successSnackBar(getStringResource(R.string.label_update_profile_success))
    }

    override fun onUpdateProfileFailed(e: Throwable?) {
        with(binding) {
            btnSave.isLoading = false
        }
    }

    override fun onUpdateAvatarSuccess(data: Profile) {
        with(binding) {
            pfProfile.url(data.avatar)
            successSnackBar(getStringResource(R.string.label_update_profile_success))
        }
    }

    override fun onUpdateAvatarFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun onSelectedZoneChanged(zoneType: String) {
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

    private val nav: MainNavigation by navigation { activity }
    private val viewModel: UpdateProfileViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()

    private val nameValidation by validation { etFullname }
    private val phoneValidation by validation { etPhoneNumber }
    private val emailValidation by validation(defaultValue = true, defaultMessage = "") { etEmail }
    private val addressValidation by validation { etAddress }
    private val provinceValidation by validation { etProvince }
    private val districtValidation by validation { etDistrict }
    private val subDistrictValidation by validation { etSubDistrict }
    private val villageValidation by validation { etVillage }
    private val jobValidation by validation { etJob }
    private val genderValidation by validation { rcGender }

    private var provinceId: String? = null
    private var districtId: String? = null
    private var subDistrictId: String? = null
    private var villageId: String? = null
    private var birthDate: String? = null

    companion object {
        private const val PROVINCE = "province"
        private const val DISTRICT = "district"
        private const val SUB_DISTRICT = "subdistrict"
    }
}
