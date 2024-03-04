package com.agree.ecosystem.utilities.presentation.menu.zone.province

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrZoneBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.LayoutProvinceBottomSheetBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province
import com.agree.ui.snackbar.errorSnackBar
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectProvinceBottomSheet(private val onProvinceSelected: (province: Province) -> Unit) :
    AgrZoneBottomSheet(), ProvinceDataContract {

    private val binding: LayoutProvinceBottomSheetBinding by viewBinding()
    private val viewModel: ProvinceViewModel by viewModel()

    private val provinceAdapter: ProvinceAdapter by lazy {
        ProvinceAdapter(onItemClick = {
            onProvinceSelected(it)
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
        addObservers(ProvinceObserver(this, viewModel))
        viewModel.getAllProvinces()
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvProvince.adapter = provinceAdapter
            rvProvince.setLegionDivider()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnClose.setOnClickListener {
                dismiss()
            }
            etSearch.doOnTextChanged { text, _, _, _ ->
                provinceAdapter.filter.filter(text)
            }
        }
    }

    override fun onLoading() {
        with(binding) {
            msvProvinceList.showLoadingLayout()
        }
    }

    override fun onGetProvinceSuccess(data: List<Province>) {
        with(binding) {
            msvProvinceList.showDefaultLayout()
            provinceAdapter.apply {
                setProvinces(data)
                addOrUpdateAll(data)
            }
        }
    }

    override fun onGetProvinceFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    companion object {
        const val TAG = "SelectProvinceBottomSheet"
    }
}
