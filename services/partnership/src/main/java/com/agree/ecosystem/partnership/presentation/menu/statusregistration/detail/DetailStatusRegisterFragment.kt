package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import androidx.core.view.isVisible
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.convertUTCTimeTo
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.FragmentDetailStatusRegisterBinding
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.FinalAssessmentStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusTracking
import com.agree.ui.snackbar.errorSnackBar
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStatusRegisterFragment :
    AgrFragment<FragmentDetailStatusRegisterBinding>(),
    DetailStatusRegistrationDataContract {

    private var submissionId: String? = null
    private var data: RegistrationStatusDetails? = null

    override fun initData() {
        super.initData()
        submissionId = requireActivity().intent?.extras?.getBundle("submissionId")?.let {
            DetailStatusRegisterFragmentArgs.fromBundle(it).submissionId
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvStatusTracking.adapter = adapter
            rvSectors.adapter = sectorAdapter
        }
    }

    override fun initAction() {
        super.initAction()
        getData()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            btnCancelSubmission.setOnClickListener {
                showConfirmationDialog()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailStatusRegistrationObserver(this, viewModel))
    }

    override fun getData() {
        submissionId?.let {
            viewModel.getData(it)
        }
    }

    override fun onGetRegistrationStatusDetailsLoading() {
        binding.msvStatusRegistrationDetails.showLoadingLayout()
    }

    override fun onGetRegistrationStatusDetailsSuccess(data: RegistrationStatusDetails) {
        with(binding) {
            binding.msvStatusRegistrationDetails.showDefaultLayout()
            this@DetailStatusRegisterFragment.data = data
            imgPartnerLogo.loadImage(data.companyLogo)
            tvPartnerName.text = data.companyName
            tvRequestDate.text = getString(
                R.string.label_status_registration_submit_date,
                data.createdAt.convertUTCTimeTo(ConverterDate.FULL_DATE)
            )
            tvRegistrationStatus.apply {
                text = data.status.alias
                setTextColor(data.status.textColor.getAttrsValue(requireContext()))
            }
            cvRegistrationStatus.setCardBackgroundColor(
                data.status.backgroundColor.getAttrsValue(
                    requireContext()
                )
            )
            tvFarmArea.text = getString(R.string.label_farm_size_value, data.size)
            tvAreaAddress.text = getString(R.string.label_full_address_value, data.address, data.villageName, data.subDistrictName, data.districtName, data.provinceName)
            cvFooter.isVisible = data.status == RegistrationStatus.Status.SUBMITTED
            sectorAdapter.clear()
            sectorAdapter.addAll(data.subSectors)
        }
    }

    override fun onGetRegistrationStatusDetailsFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun onGetRegistrationStatusTrackingLoading() {
        binding.msvTracking.showLoadingLayout()
    }

    override fun onGetRegistrationStatusTrackingSuccess(data: List<RegistrationStatusTracking>) {
        if (data.last().status == RegistrationStatusTracking.Status.SUCCESS || data.last().status == RegistrationStatusTracking.Status.FAILED) {
            getDetailFinalAssessment()
        } else {
            binding.msvTracking.showDefaultLayout()
        }
        adapter.clear()
        adapter.addOrUpdateAll(
            data.onEach {
                it.subSector = this.data?.subSectors ?: emptyList()
            }
        )
    }

    override fun onGetRegistrationStatusTrackingFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun doCancelRegistration() {
        submissionId?.let { viewModel.cancelRegistration(it) }
    }

    override fun onCancelRegistrationLoading() {
        binding.btnCancelSubmission.isLoading = true
    }

    override fun onCancelRegistrationSuccess(data: Any) {
        with(binding) {
            btnCancelSubmission.isLoading = false
            submissionId?.let {
                viewModel.getData(it)
            }
        }
    }

    override fun onCancelRegistrationFailed(e: Throwable?) {
        binding.btnCancelSubmission.isLoading = false
        requireActivity()
        errorSnackBar(e?.message.toString())
    }

    override fun showConfirmationDialog() {
        DialogUtils.showCustomDialog(
            context = requireContext(),
            title = getString(R.string.label_cancel_registration_dialog_title),
            message = getString(R.string.label_cancel_registration_dialog_subtitle),
            negativeAction = Pair(getString(R.string.action_yes)) {
                doCancelRegistration()
            },
            positiveAction = Pair(getString(R.string.action_no), null),
            autoDismiss = false
        )
    }

    override fun getDetailFinalAssessment() {
        submissionId?.let {
            viewModel.getDetailFinalAssessmentStatus(it)
        }
    }

    override fun onGetDetailFinalAssessmentLoading() {
        binding.msvTracking.showLoadingLayout()
    }

    override fun onGetDetailFinalAssessmentSuccess(data: List<FinalAssessmentStatus>) {
        binding.msvTracking.showDefaultLayout()
        adapter.setFinalAssessmentData(data)
    }

    override fun onGetDetailFinalAssessmentFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    private val viewModel: DetailStatusRegistrationViewModel by viewModel()
    private val adapter = DetailStatusRegistrationAdapter()
    private val sectorAdapter = SelectedSectorsAdapter()
    private val prefs: AgrPrefUsecase by inject()
}
