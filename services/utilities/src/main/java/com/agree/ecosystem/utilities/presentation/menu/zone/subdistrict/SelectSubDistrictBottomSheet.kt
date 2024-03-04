package com.agree.ecosystem.utilities.presentation.menu.zone.subdistrict

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrZoneBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.LayoutSubDistrictBottomSheetBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict
import com.agree.ui.snackbar.errorSnackBar
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectSubDistrictBottomSheet(
    private val districtId: String,
    private val onSubDistrictSelected: (subDistrict: SubDistrict) -> Unit
) : AgrZoneBottomSheet(),
    SubDistrictDataContract {

    private val binding: LayoutSubDistrictBottomSheetBinding by viewBinding()
    private val viewModel: SubDistrictViewModel by viewModel()

    private val subDistrictAdapter: SubDistrictAdapter by lazy {
        SubDistrictAdapter(onItemClick = {
            onSubDistrictSelected(it)
            dismiss()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(SubDistrictObserver(this, viewModel))
        viewModel.getSubDistrictsByDistrict(districtId)
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvSubDistrict.adapter = subDistrictAdapter
            rvSubDistrict.setLegionDivider()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnClose.setOnClickListener {
                dismiss()
            }
            etSearch.doOnTextChanged { text, _, _, _ ->
                subDistrictAdapter.filter.filter(text)
            }
        }
    }

    override fun onLoading() {
        with(binding) {
            msvSubDistrict.showLoadingLayout()
        }
    }

    override fun onGetSubDistrictSuccess(data: List<SubDistrict>) {
        with(binding) {
            msvSubDistrict.showDefaultLayout()
            subDistrictAdapter.apply {
                setSubDistricts(data)
                addOrUpdateAll(data)
            }
        }
    }

    override fun onGetSubDistrictFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    companion object {
        const val TAG = "SelectSubDistrictBottomSheet"
    }
}
