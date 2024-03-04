package com.agree.ecosystem.monitoring.presentation.menu.additionalactivities

interface AdditionalActivitiesDataContract {
    fun setFormEnabled(isEditable: Boolean)

    fun insertAdditionalActivity()
    fun updateAdditionalActivity()
}
