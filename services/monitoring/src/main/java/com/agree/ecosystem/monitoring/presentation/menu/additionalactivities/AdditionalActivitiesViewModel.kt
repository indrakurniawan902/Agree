package com.agree.ecosystem.monitoring.presentation.menu.additionalactivities

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.ActiveFormSchema
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ui.data.reqres.model.FormItem
import com.devbase.data.utilities.DevViewModel

class AdditionalActivitiesViewModel(
    val monitoringUseCase: MonitoringUseCase
) : DevViewModel() {
    /**
     * State Status Form to handle form status enable or not
     */
    private val _statusForm = MutableLiveData(false)
    val statusForm = _statusForm.immutable()

    /**
     * State Form Schema to Hold data form schema from Backend or
     * from previous screen, and return to backend when insert or update data
     */
    private val _formSchema = MutableLiveData<List<FormItem>>(emptyList())
    val formSchema = _formSchema.immutable()

    /**
     * State Pre Render Form Schema is [formSchema] with Modified Values to
     * display in view's, and it will only saved on memory, not for publish to Backend
     */
    private val _praRenderFormSchema = MutableLiveData<List<FormItem>>(emptyList())
    val praRenderFormSchema = _praRenderFormSchema.immutable()

    /**
     * State Active Schema is Existing form value from backend,
     * this value will used by [praRenderFormSchema]
     */
    private val _activeSchema = MutableLiveData<List<ActiveFormSchema>>()

    /**
     * Change [statusForm] value
     */
    fun toggleStatus() {
        _statusForm.value?.let {
            _statusForm.postValue(!it)
        }
    }

    /**
     * Set Form Schema data
     */
    fun setFormSchema(forms: List<FormItem>) {
        _formSchema.postValue(forms)
    }

    /**
     * Get [formSchema] data Value
     */
    fun getFormSchema(): List<FormItem> {
        return formSchema.value ?: emptyList()
    }

    /**
     * Set [praRenderFormSchema] data value
     */
    fun setPraRenderFormSchema(data: List<ActiveFormSchema>) {
        _activeSchema.postValue(data)

        val mappedSchema: MutableList<FormItem> = mutableListOf()
        data.forEach { activeFormSchema ->
            getFormSchema().find { activeFormSchema.id == it.id }?.let {
                mappedSchema.add(it.copy(value = activeFormSchema.value))
            }
        }

        _praRenderFormSchema.postValue(mappedSchema)
    }
}
