package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.detail

import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.databinding.FragmentDetailLoanPackageBinding
import com.agree.ecosystem.finances.databinding.ItemDescriptionBinding
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.ProfileFormData
import com.agree.ecosystem.finances.domain.reqres.model.ProfilePropertiesDetail
import com.agree.ecosystem.finances.domain.reqres.model.ProfileRequiredFields
import com.agree.ecosystem.finances.presentation.navigation.LoanSubmissionNavigation
import com.agree.ecosystem.finances.presentation.navigation.MenuNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailLoanPackageFragment : AgrFragment<FragmentDetailLoanPackageBinding>(),
    DetailLoanPackgeDataContract {

    private val requiredField = ArrayList<FinanceCustomMapData>()

    override fun initAction() {
        super.initAction()
        Timber.tag("PARAMM").v(
            "${args.companyId} \n ${args.mitraId}"
        )
        with(binding) {
            toolbar.navController = menuNav.getNavController()
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailLoanPackageObserver(this, viewModel))
        fetchDetailLoanPackage(args.loanPackageId)

    }

    override fun fetchDetailLoanPackage(loanPackageId: String) {
        viewModel.fetchDetailLoanPackage(loanPackageId)
    }

    override fun detailLoanPackageOnLoading() {}

    override fun detailLoanPackageOnSuccess(data: DetailLoanPackage) {
        val dataProperties = data.loanPackageDataSchema.properties

        with(binding) {
            imgLoanPackage.url = data.image
            tvLoanPackageName.text = data.loanPackageName
            tvLoanPackageDescription.text = data.loanPackageDescription
            btnRegister.setOnClickListener {
                menuNav.toLoanSubmissionActivity(
                    data,
                    data.loanPackagePaymentType,
                    requiredField,
                    "",
                    args.companyId,
                    args.mitraId
                )
            }
        }

        if (dataProperties.personalInfo.items.required.isNotEmpty()) {
            dataProperties.personalInfo.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.PERSONAL_INFO.label,
                        it,
                        dataProperties.personalInfo.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.personalInfo, ProfileFormData.PERSONAL_INFO.value)
        }

        if (dataProperties.familyData.items.required.isNotEmpty()) {
            dataProperties.familyData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.FAMILY_INFO.label,
                        it,
                        dataProperties.familyData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.familyData, ProfileFormData.FAMILY_INFO.value)
        }

        if (dataProperties.spouseData.items.required.isNotEmpty()) {
            dataProperties.spouseData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.SPOUSE_INFO.label,
                        it,
                        dataProperties.spouseData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.spouseData, ProfileFormData.SPOUSE_INFO.value)
        }

        if (dataProperties.addressData.items.required.isNotEmpty()) {
            dataProperties.addressData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.ADDRESS_INFO.label,
                        it,
                        dataProperties.addressData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.addressData, ProfileFormData.ADDRESS_INFO.value)
        }
        if (dataProperties.jobInfo.items.required.isNotEmpty()) {
            dataProperties.jobInfo.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.JOB_INFO.label,
                        it,
                        dataProperties.jobInfo.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.jobInfo, ProfileFormData.JOB_INFO.value)
        }

        if (dataProperties.emergencyData.items.required.isNotEmpty()) {
            dataProperties.emergencyData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.EMERGENCY_INFO.label,
                        it,
                        dataProperties.emergencyData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.emergencyData, ProfileFormData.EMERGENCY_INFO.value)
        }

        if (dataProperties.businessData.items.required.isNotEmpty()) {
            dataProperties.businessData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.BUSINESS_INFO.label,
                        it,
                        dataProperties.businessData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.businessData, ProfileFormData.BUSINESS_INFO.value)
        }

        if (dataProperties.assetData.items.required.isNotEmpty()) {
            dataProperties.assetData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.ASSET_INFO.label,
                        it,
                        dataProperties.assetData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.assetData, ProfileFormData.ASSET_INFO.value)
        }

        if (dataProperties.bankData.items.required.isNotEmpty()) {
            dataProperties.bankData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.BANK_INFO.label,
                        it,
                        dataProperties.bankData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.bankData, ProfileFormData.BANK_INFO.value)
        }

        if (dataProperties.photosData.items.required.isNotEmpty()) {
            dataProperties.photosData.items.required.forEach {
                requiredField.add(
                    FinanceCustomMapData(
                        ProfileFormData.PHOTOS_DATA.label,
                        it,
                        dataProperties.photosData.items.properties[it]?.title ?: ""
                    )
                )
            }
            bindDataAndAddView(dataProperties.photosData, ProfileFormData.PHOTOS_DATA.value)
        }


    }

    private fun bindDataAndAddView(data: ProfilePropertiesDetail, title: String) {
        val view = ItemDescriptionBinding.inflate(LayoutInflater.from(requireContext()))
        view.label.text = title
        view.value.text = convertProfilePropertiesItems(data.items)
        binding.llDescContainer.addView(view.root)
    }

    override fun detailLoanPackageOnError(e: Throwable?) {
    }

    private fun convertProfilePropertiesItems(items: ProfileRequiredFields): String? {
        return if (items.required.isNotEmpty()) {
            var result = ""
            items.required.forEach {
                result += "· ${items.properties[it]?.title} \n"
            }
            result
        } else {
            null
        }
    }

    private val args: DetailLoanPackageFragmentArgs by navArgs()
    private val menuNav: MenuNavigation by navigation { activity }
    private val loanSubmissionNav: LoanSubmissionNavigation by navigation { activity }
    private val viewModel: DetailLoanPackageViewModel by viewModel()
}
