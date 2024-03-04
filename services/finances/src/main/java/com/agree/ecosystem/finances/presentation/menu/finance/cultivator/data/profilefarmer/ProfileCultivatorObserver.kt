package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class ProfileCultivatorObserver(
    private val contract: ProfileCultivatorDataContract,
    private val viewModel: ProfileCultivatorViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.data.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.profileCultivatorOnLoading()
                is VmData.Success -> {
                    contract.profileCultivatorOnSuccess(it.data)
                }
                is VmData.Failure -> contract.profileCultivatorOnError(it.throwable)
                is VmData.Empty -> contract.profileCultivatorOnEmpty(null)
            }
        }
    }
}