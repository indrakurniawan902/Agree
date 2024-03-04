package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.dynamic

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DynamicFormInfoCultivatorObserver(
    private val contract: DynamicformInfoCultivatorDataContract,
    private val viewModel: DynamicFormInfoCultivatorViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.resultDataCultivator.observe(owner) {
            when (it) {
                is VmData.Loading -> contract.dynamicDataCultivatorOnLoading()
                is VmData.Success -> contract.dynamicDataCultivatorOnSuccess(it.data)
                is VmData.Failure -> contract.dynamicDataCultivatorOnError(it.throwable)
                is VmData.Empty -> contract.dynamicDataCultivatorOnEmpty(null)
                else -> {}
            }
        }
    }
}
