package com.agree.ecosystem.smartfarming.presentation.menu.monitoring.devices

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.smartfarming.databinding.FragmentDevicesSelectorDialogBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.telkom.legion.component.utility.extension.setLegionDivider

class DevicesSelectorDialogFragment(
    private val data: List<Device>,
    private val selectedDevice: Device? = null,
    private val callback: (Device?) -> Unit
) : AgrBottomSheet() {

    private val adapter: DevicesSelectorAdapter by lazy {
        DevicesSelectorAdapter(viewLifecycleOwner)
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

    override fun initUI() {
        super.initUI()
        with(binding) {
            ivClose.setOnClickListener {
                dismiss()
            }

            btnApply.setOnClickListener {
                callback.invoke(adapter.getSelectedValue())
                dismiss()
            }

            rvListTools.apply {
                adapter = this@DevicesSelectorDialogFragment.adapter.apply {
                    addAll(data)
                    emit(selectedDevice ?: data.first())
                }
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

    private val binding: FragmentDevicesSelectorDialogBinding by viewBinding()

}