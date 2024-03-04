package com.agree.ecosystem.utilities.presentation.menu.sectorsdialog

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.agree.ecosystem.core.utils.R
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.utilities.databinding.FragmentSectorsDialogBinding
import com.agree.ecosystem.utilities.databinding.LayoutErrorSubsectorBinding
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.oratakashi.viewbinding.core.tools.gone
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import com.telkom.legion.extension.snackbar.LgnSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SectorsDialogFragment(
    private val defaultValue: String,
    private val isSmartFarming: Boolean,
    private val hasSmartFarming: Boolean,
    private val callback: (Pair<List<SubSector>, Boolean>) -> Unit
) : AgrBottomSheet(), SectorsDataContract {

    private val binding: FragmentSectorsDialogBinding by viewBinding()

    private val subSectors: MutableList<SubSector> by lazy {
        ArrayList()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return initFooter(super.onCreateDialog(savedInstanceState) as BottomSheetDialog)
    }

    private fun initFooter(bottomSheetDialog: BottomSheetDialog): BottomSheetDialog {

        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val dialogLayout =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val containerLayout: FrameLayout =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.container) as FrameLayout

            val params = FrameLayout.LayoutParams(
                dialogLayout.measuredWidth,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL

            val containerFooter = binding.bottomAppBar

            (containerFooter.parent as ViewGroup).removeView(containerFooter)

            containerLayout.addView(containerFooter, params)
        }

        return bottomSheetDialog
    }

    override fun initAction() {
        with(binding) {
            ivClose.setOnClickListener { dismiss() }

            if (!isSmartFarming) {
                cbSmartfarming.gone()
                divSmartfarming.gone()
            }

            cbSelectAll.setOnClickListener {
                spSector.isCheckedAll = cbSelectAll.isChecked
            }

            cbSmartfarming.isChecked = hasSmartFarming

            btnApply.setOnClickListener {
                /**
                 * If CheckedAll and error detected will return callback empty list
                 * because nothing explicit sector selected, and fragment doesn't know
                 * how much real size sector list
                 */
                if (spSector.isCheckedAll) {
                    callback.invoke(subSectors to cbSmartfarming.isChecked)
                    dismiss()
                } else {
                    runCatching {
                        callback.invoke(
                            subSectors.filter {
                                spSector.text.contains(it.getFullSectorName())
                            } to cbSmartfarming.isChecked
                        )
                        dismiss()
                    }.onFailure {
                        it.printStackTrace()
                        callback.invoke(emptyList<SubSector>() to cbSmartfarming.isChecked)
                        dismiss()
                    }
                }
            }

            getSubSectors()
        }
    }

    override fun initObserver() {
        addObservers(SectorsObserver(this, viewModel))
    }

    override fun getSubSectors() {
        viewModel.getSubSectors()
    }

    override fun onGetSubSectorsLoading() {
        binding.msvSubSectors.showLoadingLayout()
    }

    override fun onGetSubSectorsSuccess(data: List<SubSector>) {
        with(binding) {
            binding.msvSubSectors.showDefaultLayout()
            subSectors.clear()
            subSectors.addAll(data)
            spSector.addAll(data)

            if (defaultValue == getString(R.string.label_all_subsector)) {
                spSector.text = data.joinToString(", ") { it.getFullSectorName() }
            } else {
                spSector.text = defaultValue
            }

            spSector.setOnCheckedChangeListener {
                cbSelectAll.isChecked = it
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
            LgnSnackBar.setup(this) {
                title = e?.message.toString()
                type = LgnSnackBar.ToastType.ERROR
                duration = Snackbar.LENGTH_LONG
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val viewModel: SectorsViewModel by viewModel()

    companion object {
        fun setup(block: Builder.() -> Unit): SectorsDialogFragment {
            return Builder().apply(block).build()
        }
    }

    open class Builder {
        /**
         * Selected sector
         */
        var selectedSectors: String = ""

        /**
         * Show Smartfarming Filter
         */
        var isSmartFarming: Boolean = false

        /**
         * Checked Smart Farming
         */
        var hasSmartFarming: Boolean = false

        /**
         * Callback will return list of selected subsector and isSmartFarming
         */
        private var callback: (Pair<List<SubSector>, Boolean>) -> Unit = {}

        /**
         * Callback will return list of selected subsector and isSmartFarming
         */
        @JvmName("setCallbackListenerBoth")
        fun setCallbackListener(callback: (List<SubSector>, Boolean) -> Unit) {
            this.callback = {
                callback.invoke(it.first, it.second)
            }
        }

        /**
         * Callback will return list of selected subsector only
         */
        @JvmName("setCallbackListenerSubSector")
        fun setCallbackListener(callback: (List<SubSector>) -> Unit) {
            this.callback = {
                callback.invoke(it.first)
            }
        }

        fun build(): SectorsDialogFragment {
            return SectorsDialogFragment(selectedSectors, isSmartFarming, hasSmartFarming, callback)
        }
    }
}
