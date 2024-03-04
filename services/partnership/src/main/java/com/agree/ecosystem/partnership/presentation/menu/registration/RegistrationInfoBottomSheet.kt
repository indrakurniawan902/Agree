package com.agree.ecosystem.partnership.presentation.menu.registration

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.singleLinkSpan
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.data.reqres.model.registration.CommodityBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.registration.RegistrationInfoBodyPost
import com.agree.ecosystem.partnership.data.reqres.model.registration.SubsectorBodyPost
import com.agree.ecosystem.partnership.databinding.LayoutRegistrationInfoBinding
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusDetails
import com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail.DetailStatusRegistrationViewModel
import com.agree.ecosystem.partnership.presentation.navigation.RegistrationNavigation
import com.agree.ecosystem.partnership.utils.capitalizeWords
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.toast
import com.oratakashi.viewbinding.core.tools.visible
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt

class RegistrationInfoBottomSheet(private val body: RegistrationInfoBodyPost) :
    BottomSheetDialogFragment(), RegistrationBottomSheetDataContract {

    private val binding: LayoutRegistrationInfoBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initAction()
        initObserver()
        with(binding) {
            val bottomSheetBehavior = BottomSheetBehavior.from(container.parent as View)
            bottomSheetBehavior.setBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dismiss()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        if (slideOffset < -0.8) {
                            binding.btnRegisterContainer.gone()
                        } else {
                            binding.btnRegisterContainer.visible()
                        }
                    }
                })
            btnClose.setOnClickListener { dismiss() }
            val subSector = body.subsector.filter { s -> s.commodities.isNotEmpty() }
            adapter = RegistrationInfoAdapter()
            adapter.addAll(subSector)
            rvSubSector.adapter = adapter
            adapter.apply {
                setEndlessScroll(rvSubSector)
                resetEndlessScroll()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog

            dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val containerLayout: FrameLayout =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.container) as FrameLayout
            val button = binding.btnRegisterContainer
            val params = FrameLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.BOTTOM
            val parent = button.parent as ViewGroup
            parent.removeView(button)
            containerLayout.addView(button, params)
        }
        return bottomSheetDialog
    }

    private fun initObserver() {
        addObservers(RegistrationBottomSheetObserver(this, viewModel))
    }

    private fun initAction() {
        with(binding) {
            btnRegister.setOnClickListener {
                val commodities = arrayListOf<CommodityBodyPost>()
                body.subsector.map {
                    it.commodities.map { c ->
                        commodities.add(CommodityBodyPost(c.id))
                    }
                }
                val subSectorTemp = body.subsector.filter { s -> s.commodities.isNotEmpty() }

                val subSector = arrayListOf<SubsectorBodyPost>()
                subSectorTemp.map {
                    subSector.add(SubsectorBodyPost(it.id))
                }
                val postBody = RegistrationBodyPost(
                    body.size, subSector, body.type, body.address,
                    body.provinceId, body.districtId, body.subdistrictId,
                    body.villageId, body.companyId, commodities
                )
                if (!cbTnc.isChecked) {
                    tvTncError.visible()
                    val bottomSheetBehavior = BottomSheetBehavior.from(container.parent as View)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    scrollView.scrollTo(0, tvTncError.y.roundToInt() + 200)
                    linearTnc.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_stroke)
                } else {
                    viewModel.registrationPartnership(postBody)
                }
            }
            cbTnc.setOnCheckedChangeListener { _, _ ->
                tvTncError.gone()
                linearTnc.background = null
            }
        }
    }

    private fun initUi() {
        with(binding) {
            val string: Spanned = Html.fromHtml(
                "${getString(R.string.label_register_as_partner)} <b>${body.companyName}</b> ${
                getString(R.string.label_with_the_following_information)
                }"
            )
            tvRegistrationInfoLabel.text = string
            tvLuasDaerah.text = "${body.size} Ha"
            tvAddress.text =
                "${body.address}, ${getString(R.string.label_villages)} ${body.villageName.capitalizeWords()}, ${
                getString(R.string.label_sub_district)
                }  ${body.subdistrictName.capitalizeWords()}, ${body.districtName.capitalizeWords()}, ${body.provinceName.capitalizeWords()}"
            tvTnc.apply {
                text = getString(R.string.label_tnc_checkbox).singleLinkSpan(requireContext()) {
                    navigation.fromRegistrationToTnc()
                }
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    override fun onRegistrationSuccess(data: RegistrationStatusDetails) {
        binding.btnRegister.isLoading = false
        viewModeStatus.setData(data)
        navigation.fromRegistrationToStatusRegistration(data)
    }

    override fun onRegistrationLoading() {
        binding.btnRegister.isLoading = true
    }

    override fun onRegistrationEmpty() {
        binding.btnRegister.isLoading = false
    }

    override fun onRegistrationFailed(e: Throwable?) {
        super.onRegistrationFailed(e)
        binding.btnRegister.isLoading = false
        toast(e?.message.toString())
    }

    private lateinit var adapter: RegistrationInfoAdapter

    companion object {
        const val TAG = "RegistrationSuccessBottomSheet"
    }

    private val viewModel: RegistrationViewModel by sharedViewModel()
    private val viewModeStatus: DetailStatusRegistrationViewModel by sharedViewModel()
    private val navigation: RegistrationNavigation by navigation { activity }
}
