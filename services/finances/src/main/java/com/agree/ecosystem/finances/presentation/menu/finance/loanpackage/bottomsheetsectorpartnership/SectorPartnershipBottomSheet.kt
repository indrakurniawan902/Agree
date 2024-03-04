package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.bottomsheetsectorpartnership

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.finances.databinding.FragmentSectorPartnershipBottomSheetBinding
import com.agree.ecosystem.utilities.databinding.LayoutErrorSubsectorBinding
import com.agree.ui.snackbar.errorSnackBar
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SectorPartnershipBottomSheet(
    private val callBack: (SubSector) -> Unit,
    private val datasSubSector: MutableList<SubSector>
) : AgrBottomSheet(),
    com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDataContract {

    private val viewModel: com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsViewModel by sharedViewModel()
    private val binding: FragmentSectorPartnershipBottomSheetBinding by viewBinding()
    private val adapter: SectorPartnershipBottomSheetAdapter by lazy {
        SectorPartnershipBottomSheetAdapter({
            if (it != null) {
                callBack.invoke(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvSubSector.adapter = adapter
            rvSubSector.setLegionDivider()
        }
    }

    override fun initAction() {
        with(binding) {
            ivClose.setOnClickListener { dismiss() }
            init(datasSubSector)
        }
    }

    override fun initObserver() {
        addObservers(
            com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsObserver(
                this,
                viewModel
            )
        )
    }

    override fun onGetSubSectorsLoading() {
        binding.msvSubSectors.showLoadingLayout()
    }

    override fun onGetSubSectorsSuccess(data: List<SubSector>) {
        init(data)
    }

    private fun init(data: List<SubSector>) {
        with(binding) {
            binding.msvSubSectors.showDefaultLayout()
            adapter.apply {
                setEndlessScroll(rvSubSector)
                resetEndlessScroll()
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
    }

    override fun onGetSubSectorsFailed(e: Throwable?) {
        with(binding) {
            msvSubSectors.showErrorLayout()

            val errorView = msvSubSectors.getView(ViewState.ERROR)
            with(LayoutErrorSubsectorBinding.bind(errorView)) {
                btnTryAgain.setOnClickListener {
                    getSubSectors()
                }
            }
        }
        if (AppConfig.isDebug) {
            errorSnackBar(e?.message.toString())
        }
    }

    override fun getSubSectors() {
//        viewModel.getSubSectors()
    }
}
