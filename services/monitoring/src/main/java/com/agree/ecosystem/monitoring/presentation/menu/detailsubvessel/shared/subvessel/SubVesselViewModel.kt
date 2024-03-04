package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel

import androidx.lifecycle.viewModelScope
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.devbase.data.utilities.DevViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

class SubVesselViewModel : DevViewModel() {
    /**
     * State for SubVesselId
     */
    private val _subVesselId = MutableStateFlow("")
    val subVesselId = _subVesselId.immutable()

    /**
     * State for Detail SubVessel
     */
    private val _subVessel: MutableStateFlow<DetailSubVessel?> = MutableStateFlow(null)
    val subVessel = _subVessel.immutable()

    /**
     * State status in Detail SubVessel for Action Add Activity
     */
    private val _statusSubVessel = MutableStateFlow("")
    val statusSubVessel = _statusSubVessel.immutable()

    /**
     * State Error in Activities for Action Button Visibility
     */
    private val _isActivitiesError = MutableStateFlow(true)
    val isActivitiesError = _isActivitiesError.immutable()

    /**
     * State Error in Transaction for Action Button Visibility
     */
    private val _isTransactionError = MutableStateFlow(true)
    val isTransactionError = _isTransactionError.immutable()

    /**
     * State Error in Incident for Action Button Visibility
     */
    private val _isIncidentError = MutableStateFlow(true)
    val isIncidentError = _isIncidentError.immutable()

    private val _isStateChanged = MutableStateFlow(0)
    val isStateChanged = _isStateChanged.immutable()

    /**
     * State Empty in Additional Activity
     */
    private val _isEmptyAdditionalActivities = MutableStateFlow(true)
    val isEmptyAdditionalActivities = _isEmptyAdditionalActivities.immutable()

    /**
     * State for Company Id
     */
    private val _companyId = MutableStateFlow("")
    val companyId = _companyId.immutable()

    /**
     * State for Commodity Id
     */
    private val _commodityId = MutableStateFlow("")
    val commodityId = _commodityId.immutable()

    /**
     * State for name Vessel
     */
    private val _subVesselName = MutableStateFlow("")
    val subVesselName = _subVesselName.immutable()

    /**
     * State for Tab Layout
     */
    private val _tabLayout = MutableStateFlow(TabLayout.Tab())
    val tabLayout = _tabLayout.immutable()

    fun setTabLayoutSelected(tab: TabLayout.Tab) {
        viewModelScope.launch {
            _tabLayout.emit(tab)
        }
    }

    fun setSubVesselId(id: String) {
        viewModelScope.launch {
            if (_subVesselId.value != id) {
                _subVesselId.emit(id)
            }
        }
    }

    fun setStatusSubVessel(data: String) {
        viewModelScope.launch { _statusSubVessel.emit(data) }
    }

    fun getStatusSubVessel(): String {
        return statusSubVessel.value
    }

    fun setIsEmptyAdditionalActivities(isEmpty: Boolean) {
        viewModelScope.launch { _isEmptyAdditionalActivities.emit(isEmpty) }
    }

    fun getSubVesselId(): String {
        return subVesselId.value
    }

    fun setSubVessel(subVessel: DetailSubVessel) {
        viewModelScope.launch { _subVessel.emit(subVessel) }
    }

    fun getSubVessel(): DetailSubVessel? {
        return subVessel.value
    }

    fun setActivitiesState(state: Boolean) {
        viewModelScope.launch { _isActivitiesError.emit(state) }
    }

    fun getActivitiesState(): Boolean {
        return isActivitiesError.value
    }

    fun setIncidentState(state: Boolean) {
        viewModelScope.launch { _isIncidentError.emit(state) }
    }

    fun getIncidentState(): Boolean {
        return isIncidentError.value
    }

    fun setCompanyId(companyId: String) {
        viewModelScope.launch { _companyId.emit(companyId) }
    }

    fun getCompanyId(): String {
        return companyId.value
    }

    fun setCommodityId(commodityId: String) {
        viewModelScope.launch { _commodityId.emit(commodityId) }
    }

    fun getCommodityId(): String {
        return commodityId.value
    }

    fun observeState() {
        viewModelScope.launch {
            combine(
                listOf(
                    subVessel.map { it == null },
                    isActivitiesError,
                    isTransactionError,
                    isIncidentError
                )
            ) {
                _isStateChanged.emit(Random.nextInt())
            }.collect()
        }
    }

    fun setSubVesselName(name: String) {
        viewModelScope.launch { _subVesselName.emit(name) }
    }

    fun getSubVesselName(): String {
        return subVesselName.value
    }
}
