package com.agree.ecosystem.agreepedia.presentation.menu.dialog

import com.agree.ecosystem.agreepedia.utility.enums.FilterSector
import com.agree.ecosystem.agreepedia.utility.enums.FilterSort

interface SectorDialogDataContract {
    fun onSortChange(sort: FilterSort)
    fun onSectorChange(sector: FilterSector)

    fun String.toFilterSort(): FilterSort? {
        return when (this) {
            FilterSort.LATEST.value -> FilterSort.LATEST
            FilterSort.OLDEST.value -> FilterSort.OLDEST
            else -> null
        }
    }

    fun String.toFilterSector(): FilterSector? {
        return when (this) {
            FilterSector.SHOWALL.value -> FilterSector.SHOWALL
            FilterSector.AGRICULTURE.value -> FilterSector.AGRICULTURE
            FilterSector.FISHERY.value -> FilterSector.FISHERY
            FilterSector.FARM.value -> FilterSector.FARM
            else -> null
        }
    }
}
