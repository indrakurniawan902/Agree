package com.agree.ecosystem.utilities.presentation.menu.zone.district

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrZoneBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.LayoutDistrictBottomSheetBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.District
import com.agree.ui.snackbar.errorSnackBar
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectDistrictBottomSheet(
    private val provinceId: String,
    private val onDistrictSelected: (district: District) -> Unit
) :
    AgrZoneBottomSheet(), DistrictDataContract {

    private val binding: LayoutDistrictBottomSheetBinding by viewBinding()
    private val viewModel: DistrictViewModel by viewModel()

    private val districtAdapter: DistrictAdapter by lazy {
        DistrictAdapter(onItemClick = {
            onDistrictSelected(it)
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
        addObservers(DistrictObserver(this, viewModel))
        viewModel.getDistrictsByProvince(provinceId)
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvDistrict.adapter = districtAdapter
            rvDistrict.setLegionDivider()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnClose.setOnClickListener {
                dismiss()
            }
            etSearch.doOnTextChanged { text, _, _, _ ->
                districtAdapter.filter.filter(text)
            }
        }
    }

    override fun onLoading() {
        with(binding) {
            msvDistrictList.showLoadingLayout()
        }
    }

    override fun onGetDistrictSuccess(data: List<District>) {
        with(binding) {
            msvDistrictList.showDefaultLayout()
            districtAdapter.apply {
                setDistricts(data)
                addOrUpdateAll(data)
            }
        }
    }

    override fun onGetDistrictFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    companion object {
        const val TAG = "SelectDistrictBottomSheet"
    }
}
