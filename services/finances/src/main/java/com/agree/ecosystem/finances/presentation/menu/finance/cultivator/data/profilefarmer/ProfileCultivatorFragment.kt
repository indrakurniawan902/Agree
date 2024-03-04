package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertTimeToNewTimeFormat
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.FragmentProfileCultivatorBinding
import com.agree.ecosystem.finances.domain.reqres.model.ProfileFormData
import com.agree.ecosystem.finances.domain.reqres.model.params.DynamicFormProfileCultivatorParams
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.ProfileFarmer
import com.agree.ecosystem.finances.presentation.navigation.ProfileCultivatorNavigation
import com.agree.ecosystem.finances.utils.myTimber
import com.devbase.utils.ext.isNotNull
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileCultivatorFragment : AgrFragment<FragmentProfileCultivatorBinding>(),
    ProfileCultivatorDataContract {

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvListInfoCultivatorItem.adapter = adapter
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { profileCultivatorNav.navigateUp() }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(ProfileCultivatorObserver(this, viewModel))
        fetchProfileCultivator(
            args.params.cultivator?.borrowerId ?: args.params.applyLoanCultivator?.borrowerId ?: ""
        )
    }

    override fun fetchProfileCultivator(id: String) {
        viewModel.fetchProfileCultivator(id)
    }

    override fun profileCultivatorOnLoading() {
    }

    override fun profileCultivatorOnSuccess(data: ProfileFarmer) {
        with(binding) {
            adapter.apply {
                adapter.clear()
                adapter.addOrUpdateAll(data.detailsInfo)
            }
            tvValueName.text = data.name
            tvValueNoHp.text = data.contactNumber
            ivCultivatorPhoto.url = data.image
            tvBornDate.text = data.placeOfBirth
            tvBornPlace.text = data.dateOfBirth.convertTimeToNewTimeFormat(
                ConverterDate.UTC1,
                ConverterDate.SHORT_DATE
            )


            if (isValueValid(data.gender)) {
                tvValueGender.text = when (data.gender) {
                    "0" -> getString(R.string.label_male)
                    "1" -> getString(R.string.label_female)
                    else -> "-"
                }
            } else tvValueGender.text = "-"

            tvValueNik.text = data.nik.chunked(4).joinToString(" ")
        }
    }

    override fun profileCultivatorOnEmpty(data: ProfileFarmer?) {
        myTimber("DATAA", "EMPTY")
    }

    override fun profileCultivatorOnError(e: Throwable?) {
        myTimber("DATAA", "ERROR ${e.toString()}")
    }

    private fun isValueValid(value: String): Boolean {
        var isValid = true
        value.apply {
            (isNotNull() && isNotBlank() && isNotEmpty() && this != "null").also { isValid = it }
        }
        return isValid
    }

    private val adapter: ProfileCultvatorAdapter by lazy {
        ProfileCultvatorAdapter({ data ->
            data?.let {
                when (data.keys.first()) {
                    ProfileFormData.PERSONAL_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.PERSONAL_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.personalInfo?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.FAMILY_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.FAMILY_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.familyData?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.SPOUSE_INFO.label -> {
                        var filteredDataRequired: List<String>? = null
                        if (args.params.isFromLoan) {
                            val dataRequired =
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.spouseData?.items?.required as ArrayList<String>
                            filteredDataRequired = addingDataMandatory(
                                dataRequired, listOf(
                                    "spouseName",
                                    "spouseNik",
                                    "spouseGender",
                                    "spouseDateOfBirth",
                                    "spousePhoneNumber"
                                )
                            )
                        }


                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.SPOUSE_INFO.value,
                                filteredDataRequired?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.ADDRESS_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.ADDRESS_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.addressData?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.JOB_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.JOB_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.jobInfo?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.EMERGENCY_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.EMERGENCY_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.emergencyData?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.BUSINESS_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.BUSINESS_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.businessData?.items?.required?.toTypedArray()
                            )

                        )
                    }

                    ProfileFormData.ASSET_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.ASSET_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.assetData?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.BANK_INFO.label -> {
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.BANK_INFO.value,
                                args.params.detailLoanPackage?.loanPackageDataSchema?.properties?.bankData?.items?.required?.toTypedArray()
                            )
                        )
                    }

                    ProfileFormData.PHOTOS_DATA.label -> {
                        var filteredDataRequired: List<String>? = null

                        if (args.params.isFromLoan) {
                            val dataReqired =
                                args.params.detailLoanPackage?.loanPackageDataSchema
                                    ?.properties?.photosData?.items?.required as ArrayList<String>
                            filteredDataRequired =
                                addingDataMandatory(dataReqired, listOf("spouseKtpPhoto"))
                        }
                        goToDynamicInfoFragment(
                            DynamicFormProfileCultivatorParams(
                                args.params.cultivator?.borrowerId
                                    ?: args.params.applyLoanCultivator?.borrowerId ?: "",
                                it.keys.first(),
                                ProfileFormData.PHOTOS_DATA.value,
                                filteredDataRequired?.toTypedArray()
                            )
                        )
                    }
                }
            }
        }, false)
    }

    private fun addingDataMandatory(
        args: ArrayList<String>,
        dataMandatory: Collection<String>
    ): List<String> {
        var dataRequired: ArrayList<String>?

        dataRequired = args
        dataRequired.addAll(dataMandatory)

        return dataRequired.distinct()
    }


    private fun goToDynamicInfoFragment(
        params: DynamicFormProfileCultivatorParams
    ) {
        profileCultivatorNav.toDynamicFormProfileCultivator(params)
    }

    private val profileCultivatorNav: ProfileCultivatorNavigation by navigation { activity }
    private val args: ProfileCultivatorFragmentArgs by navArgs()
    private val prefs: AgrPrefUsecase by inject()
    private val viewModel: ProfileCultivatorViewModel by viewModel()
}
