package com.agree.ecosystem.utilities.presentation.menu.zone.village

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrZoneBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.LayoutVillageBottomSheetBinding
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village
import com.agree.ui.snackbar.errorSnackBar
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectVillageBottomSheet(
    private val subDistrictId: String,
    private val onVillageSelected: (village: Village) -> Unit
) : AgrZoneBottomSheet(), VillageDataContract {

    private val binding: LayoutVillageBottomSheetBinding by viewBinding()
    private val viewModel: VillageViewModel by viewModel()

    private val villageAdapter: VillageAdapter by lazy {
        VillageAdapter(onItemClick = {
            onVillageSelected(it)
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
        addObservers(VillageObserver(this, viewModel))
        viewModel.getVillagesBySubDistrict(subDistrictId)
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvVillage.adapter = villageAdapter
            rvVillage.setLegionDivider()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnClose.setOnClickListener {
                dismiss()
            }
            etSearch.doOnTextChanged { text, _, _, _ ->
                villageAdapter.filter.filter(text)
            }
        }
    }

    override fun onLoading() {
        with(binding) {
            msvVillageList.showLoadingLayout()
        }
    }

    override fun onGetVillageSuccess(data: List<Village>) {
        with(binding) {
            msvVillageList.showDefaultLayout()
            villageAdapter.apply {
                setVillages(data)
                addOrUpdateAll(data)
            }
        }
    }

    override fun onGetVillageFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    companion object {
        const val TAG = "SelectVillageBottomSheet"
    }
}
