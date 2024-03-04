package com.agree.ecosystem.smartfarming.presentation.menu.monitoring.devices

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.smartfarming.databinding.ItemListToolsBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding
import com.telkom.legion.component.utility.extension.requiredColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DevicesSelectorAdapter(
    private val lifecycleOwner: LifecycleOwner
) : DevRecyclerViewAdapter<Device>() {

    private val state by lazy {
        MutableStateFlow<Device?>(null)
    }

    fun emit(data: Device) {
        lifecycleOwner.lifecycleScope.launch {
            state.emit(data)
        }
    }

    /**
     * Method to get selected value from adapter
     */
    fun getSelectedValue(): Device? {
        return state.value
    }

    inner class ViewHolder(private val bindLayout: ItemListToolsBinding) :
        DevItemViewHolder<Device>(bindLayout) {
        override fun bind(data: Device?) {
            with(bindLayout) {
                data?.let { toolsData ->
                    tvDeviceName.text = toolsData.deviceName.uppercase()
                    tvServiceName.text = toolsData.serviceName

                    root.setOnClickListener {
                        emit(toolsData)
                    }

                    rbTools.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            emit(toolsData)
                        }
                    }

                    lifecycleOwner.lifecycleScope.launch {
                        state.collect {
                            if (it != null && it == toolsData) {
                                root.setBackgroundColor(root.context.requiredColor(UiKitAttrs.colorPrimary200))
                                rbTools.isChecked = true
                            } else {
                                root.setBackgroundColor(root.context.requiredColor(UiKitAttrs.white))
                                rbTools.isChecked = false
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Device> {
        return ViewHolder(inflateBinding(parent))
    }
}