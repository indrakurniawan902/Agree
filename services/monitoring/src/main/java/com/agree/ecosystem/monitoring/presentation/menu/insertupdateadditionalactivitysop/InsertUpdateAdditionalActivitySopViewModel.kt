package com.agree.ecosystem.monitoring.presentation.menu.insertupdateadditionalactivitysop

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.core.utils.utility.toString
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.ActiveFormSchema
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.InsertActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.UpdateSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ui.data.reqres.model.DisplayedSchema
import com.agree.ui.data.reqres.model.FormItem
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.utils.ext.toDate
import com.google.gson.GsonBuilder
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

class InsertUpdateAdditionalActivitySopViewModel(
    val monitoringUseCase: MonitoringUseCase
) : DevViewModel() {

    private val _toggleStatus = MutableLiveData<Boolean>(false)
    val toggleStatus = _toggleStatus.immutable()

    private val _insertDetailResponse =
        DevData<InsertActivitySop>().apply { default() }
    val insertDetailResponse = _insertDetailResponse.immutable()

    private val _updateDetailResponse = DevData<SopActivityDetailBodyPost>().apply { default() }
    val updateDetailResponse = _updateDetailResponse.immutable()

    private val _uploadPhotoResponse = DevData<String>().apply { default() }
    val uploadPhotoResponse = _uploadPhotoResponse.immutable()

    private val _formSchemaData = DevData<List<AdditionalSopActivityDetail>>().apply { default() }
    val formSchemaData = _formSchemaData.immutable()

    private val _toRenderFormSchema = MutableLiveData<ArrayList<FormItem>>(ArrayList())
    val toRenderFormSchema = _toRenderFormSchema.immutable()

    private val _activeSchema = MutableLiveData<ArrayList<ActiveFormSchema>>(ArrayList())
    val activeSchema = _activeSchema.immutable()

    private val _referenceSchema = MutableLiveData<ArrayList<FormItem>>(ArrayList())
    val referenceSchema = _referenceSchema.immutable()

    fun changeToggleStatus() {
        _toggleStatus.value?.let {
            _toggleStatus.value = !it
        }
    }

    fun insertAdditionalActivity(
        idBundle: InsertActivitySopBundleParams,
        userId: String,
        activityName: String,
        order: String,
        date: String
    ) {
        val gson = GsonBuilder().create()
        var formSchema: FormSchema? = null
        val currentDateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        var dateTime = ""
        _activeSchema.value?.let { active ->
            _referenceSchema.value?.let { reference ->
                formSchema = FormSchema(
                    activeformSchema = active.map {
                        if (it.id == "ID01") {
                            it.copy(
                                value = date.toDate(ConverterDate.SQL_DATE.formatter)
                                    ?.toString(ConverterDate.SIMPLE_DATE).orEmpty()
                            )
                        } else {
                            it
                        }
                    },
                    formSchemaItem = reference,
                )
                dateTime = date.toDate(ConverterDate.SQL_DATE.formatter)
                    ?.toString(ConverterDate.UTC2.formatter).orEmpty()
            }
        }
        val data = gson.toJsonTree(formSchema).toString()

        monitoringUseCase.addNewAdditionalActivity(
            InsertActivitySopBodyPost(
                commodityId = idBundle.commodityId,
                commodityVarietyId = idBundle.commodityVarietyId,
                companyId = idBundle.companyId,
                companyMemberId = idBundle.companyMemberId,
                createdAt = currentDateTime,
                createdBy = userId,
                data = data,
                dateTime = dateTime,
                modifiedAt = currentDateTime,
                modifiedBy = userId,
                name = activityName,
                order = order,
                phaseActivityId = idBundle.phaseActivityId,
                status = Constant.INSERT_STATUS_ADDITIONAL,
                subvesselId = idBundle.subVesselId,
                vesselId = idBundle.vesselId,
                workerId = idBundle.workerId,
                individualId = String()
            )
        ).setHandler(_insertDetailResponse).let { return@let disposable::add }
    }

    fun updateActivityDetail(
        id: String,
        fieldId: String = "id",
        userId: String,
        date: String
    ) {
        val gson = GsonBuilder().create()
        var formSchema: FormSchema? = null
        val currentDateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        var dateTime = ""
        _activeSchema.value?.let { active ->
            _referenceSchema.value?.let { reference ->
                formSchema = FormSchema(
                    activeformSchema = active.map {
                        if (it.id == "ID01") {
                            it.copy(
                                value = date.toDate(ConverterDate.SQL_DATE.formatter)
                                    ?.toString(ConverterDate.SIMPLE_DATE).orEmpty()
                            )
                        } else {
                            it
                        }
                    },
                    formSchemaItem = reference,
                )
                dateTime = date.toDate(ConverterDate.SQL_DATE.formatter)
                    ?.toString(ConverterDate.UTC2.formatter).orEmpty()
            }
        }
        val data = gson.toJsonTree(formSchema).toString()
        monitoringUseCase.updateDetailActivity(
            UpdateSopActivityDetailParams(
                id = id,
                fieldId = fieldId,
                data = SopActivityDetailBodyPost(
                    data = data,
                    modifiedBy = userId,
                    modifiedAt = currentDateTime,
                    dateTime = dateTime
                )
            )
        ).setHandler(_updateDetailResponse)
            .let { return@let disposable::add }
    }

    fun uploadPhoto(
        photo: File
    ) {
        monitoringUseCase.uploadPhoto(photo)
            .setHandler(_uploadPhotoResponse)
            .let { return@let disposable::add }
    }

    fun getInsertFormSchemaData(
        params: AdditionalSopActivityDetailParams
    ) {
        monitoringUseCase.getDetailAdditionalActivitySop(params)
            .setHandler(_formSchemaData)
            .let { return@let disposable::add }
    }

    fun insertReferenceSchema(referenceFormSchema: List<FormItem>) {
        _referenceSchema.value = ArrayList(referenceFormSchema)
    }

    fun updateToRenderFormData(activeFormSchema: List<ActiveFormSchema>) {
        updateActiveFormSchema(activeFormSchema)

        val mappedSchema: MutableList<FormItem> = mutableListOf()
        _activeSchema.value?.let { active ->
            _referenceSchema.value?.let { reference ->
                active.forEach { activeSchema ->
                    reference.find { referenceSchema ->
                        activeSchema.id == referenceSchema.id
                    }?.let {
                        mappedSchema.add(
                            it.copy(value = activeSchema.value)
                        )
                    }
                }
            }
        }

        _toRenderFormSchema.value = ArrayList(mappedSchema.subList(1, mappedSchema.size))
    }

    fun updateActiveSchemaValueAt(value: String, tag: String) {
        _activeSchema.value?.let {
            val index = it.indexOfFirst { activeSchema ->
                activeSchema.id == tag
            }
            it[index].value = value
        }
    }

    fun displayedSchemaToActiveSchema(displayedSchema: List<DisplayedSchema>) {
        val mappedActiveSchema: MutableList<ActiveFormSchema> = mutableListOf()

        displayedSchema.forEach { displayedSchemaItem ->
            _activeSchema.value?.let { active ->
                active.find { activeSchema ->
                    activeSchema.id == displayedSchemaItem.id
                }?.let {
                    mappedActiveSchema.add(it)
                } ?: run {
                    mappedActiveSchema.add(
                        ActiveFormSchema(
                            id = displayedSchemaItem.id,
                            value = ""
                        )
                    )
                }
            }
        }
        _activeSchema.value = ArrayList(mappedActiveSchema)
    }

    private fun updateActiveFormSchema(newActiveFormSchema: List<ActiveFormSchema>) {
        _activeSchema.value = ArrayList(newActiveFormSchema)
    }
}
