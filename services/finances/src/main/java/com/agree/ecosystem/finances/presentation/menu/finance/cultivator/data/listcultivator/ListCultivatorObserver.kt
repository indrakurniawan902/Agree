package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class ListCultivatorObserver(
    private val contract: ListCultivatorDataContract,
    private val viewModel: ListCultivatorViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.lists.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.listCultivatorOnLoading()
                is VmData.Success -> {
                    contract.listCultivatorOnSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> contract.listCultivatorOnError(it.throwable)
                is VmData.Empty -> contract.listCultivatorOnEmpty(listOf())
            }
        }

        viewModel.loadMoreLists.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.loadMoreListCultivatorOnLoading()
                is VmData.Success -> {
                    contract.loadMoreListCultivatorOnSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> contract.loadMoreListCultivatorOnError(it.throwable)
                is VmData.Empty -> contract.loadMoreListCultivatorOnEmpty(listOf())
            }
        }


    }
}
