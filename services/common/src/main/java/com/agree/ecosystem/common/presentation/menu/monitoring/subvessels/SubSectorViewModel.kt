package com.agree.ecosystem.common.presentation.menu.monitoring.subvessels

import androidx.lifecycle.viewModelScope
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.data.utilities.DevViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubSectorViewModel : DevViewModel() {
    private val _selectedSectors: MutableStateFlow<SubSector?> by lazy {
        MutableStateFlow(null)
    }

    val selectedSector: StateFlow<SubSector?> = _selectedSectors.immutable()

    fun setSubSector(subSector: SubSector?) {
        viewModelScope.launch {
            _selectedSectors.emit(subSector)
        }
    }

    fun validateAndRemove(fullNameSector: String) {
        if (selectedSector.value?.getFullSectorName().orEmpty() == fullNameSector) {
            clear()
        }
    }

    fun clear() {
        _selectedSectors.value = null
    }

    fun validateAndRemove(subSectors: List<SubSector>) {
        _selectedSectors.value?.let { subSector ->
            if (!subSectors.contains(subSector)) {
                clear()
            }
        }
    }
}
