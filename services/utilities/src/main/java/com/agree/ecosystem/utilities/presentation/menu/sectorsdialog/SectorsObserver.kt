package com.agree.ecosystem.utilities.presentation.menu.sectorsdialog

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class SectorsObserver(
    private val view: SectorsDataContract,
    private val viewModel: SectorsViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.subSectors.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetSubSectorsLoading()
                is VmData.Success -> view.onGetSubSectorsSuccess(it.data)
                is VmData.Failure -> view.onGetSubSectorsFailed(it.throwable)
                is VmData.Empty -> view.onGetSubSectorsFailed(EmptyException())
            }
        }
    }
}
