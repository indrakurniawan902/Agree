package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.core.utils.utility.toString
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.ActiveFormSchema
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.UpdateActivitySopBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.InsertActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.UpdateSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ui.data.reqres.model.DisplayedSchema
import com.agree.ui.data.reqres.model.FormItem
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.utils.ext.toDate
import com.google.gson.GsonBuilder
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

class DetailActivitySopViewModel(
    private val monitoringUseCase: MonitoringUseCase,
) : DevViewModel() {

    private val _detailSop = DevData<List<SopActivityDetail>>().apply { default() }
    val detailSop = _detailSop.immutable()

    private val _toggleStatus = MutableLiveData<Boolean>(false)
    val toggleStatus: LiveData<Boolean> get() = _toggleStatus

    private val _toRenderFormSchema = MutableLiveData<ArrayList<FormItem>>(ArrayList())
    val toRenderFormSchema = _toRenderFormSchema.immutable()

    private val _activeSchema = MutableLiveData<ArrayList<ActiveFormSchema>>(ArrayList())
    val activeSchema = _activeSchema.immutable()

    private val _referenceSchema = MutableLiveData<ArrayList<FormItem>>(ArrayList())
    val referenceSchema = _referenceSchema.immutable()

    private val _updateDetailResponse = DevData<SopActivityDetailBodyPost>().apply { default() }
    val updateDetailResponse = _updateDetailResponse.immutable()

    private val _insertDetailReponse =
        DevData<InsertActivitySop>().apply { default() }
    val insertDetailResponse = _insertDetailReponse.immutable()

    private val _uploadPhotoResponse = DevData<String>().apply { default() }
    val uploadPhotoResponse = _uploadPhotoResponse.immutable()

    private val _updateDetailResponseSop = DevData<String>().apply { default() }
    val updateDetailResponseSop = _updateDetailResponseSop.immutable()

    private val _validationActivityDetail = DevData<ValidationActivityDetail>().apply { default() }
    val validationActivityDetail = _validationActivityDetail.immutable()

    fun changeToogleStatus() {
        _toggleStatus.value?.let {
            _toggleStatus.value = !it
        }
    }

    fun getActivityDetailData(
        id: String,
        date: String,
        subVesselId: String,
        individualId: String?
    ) {
        monitoringUseCase.getActivityDetailSop(id, date, subVesselId, individualId)
            .setHandler(_detailSop)
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

    fun updateActivityDetail(
        id: String,
        fieldId: String = "id",
        userId: String,
        date: String
    ) {
        val gson = GsonBuilder().create()
        var formSchema: FormSchema? = null
        _activeSchema.value?.let { active ->
            _referenceSchema.value?.let { reference ->
                formSchema = FormSchema(
                    activeformSchema = active.map {
                        if (it.id == "ID01") {
                            it.copy(
                                value = date.toDate(ConverterDate.UTC2.formatter)
                                    ?.toString(ConverterDate.SIMPLE_DATE).orEmpty()
                            )
                        } else {
                            it
                        }
                    },
                    formSchemaItem = reference,
                )
            }
        }
        val data = gson.toJsonTree(formSchema).toString()
        val currentDateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())

        monitoringUseCase.updateDetailActivity(
            UpdateSopActivityDetailParams(
                id = id,
                fieldId = fieldId,
                data = SopActivityDetailBodyPost(
                    data = data,
                    modifiedBy = userId,
                    modifiedAt = currentDateTime,
                    dateTime = date
                )
            )
        ).setHandler(_updateDetailResponse)
            .let { return@let disposable::add }
    }

    fun insertActivityDetail(
        idBundle: InsertActivitySopBundleParams,
        userId: String,
        activityName: String,
        order: String,
        date: String,
        individualId: String
    ) {
        val gson = GsonBuilder().create()
        var formSchema: FormSchema? = null
        val currentDateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        _activeSchema.value?.let { active ->
            _referenceSchema.value?.let { reference ->
                formSchema = FormSchema(
                    activeformSchema = active.map {
                        if (it.id == "ID01") {
                            it.copy(
                                value = date.toDate(ConverterDate.UTC2.formatter)
                                    ?.toString(ConverterDate.SIMPLE_DATE).orEmpty()
                            )
                        } else {
                            it
                        }
                    },
                    formSchemaItem = reference,
                )
//                dateTime = active[0].value.toDate(ConverterDate.SIMPLE_DATE.formatter)
//                    ?.toString(ConverterDate.UTC2.formatter).orEmpty()
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
                dateTime = date,
                modifiedAt = currentDateTime,
                modifiedBy = userId,
                name = activityName,
                order = order,
                phaseActivityId = idBundle.phaseActivityId,
                status = Constant.INSERT_STATUS_PRIMARY,
                subvesselId = idBundle.subVesselId,
                vesselId = idBundle.vesselId,
                workerId = idBundle.workerId,
                individualId = individualId
            )
        ).setHandler(_insertDetailReponse).let { return@let disposable::add }
    }

    fun updateActivityDetailSop(
        idBundle: InsertActivitySopBundleParams,
        date: String,
        update: List<IndividualSubVessel>? = null,
        delete: List<IndividualSubVessel>? = null,
        insert: List<IndividualSubVessel>? = null,
    ) {
        val gson = GsonBuilder().create()
        var formSchema: FormSchema? = null
        val currentDateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        _activeSchema.value?.let { active ->
            _referenceSchema.value?.let { reference ->
                formSchema = FormSchema(
                    activeformSchema = active.map {
                        if (it.id == "ID01") {
                            it.copy(
                                value = date.toDate(ConverterDate.UTC2.formatter)
                                    ?.toString(ConverterDate.SIMPLE_DATE).orEmpty()
                            )
                        } else {
                            it
                        }
                    },
                    formSchemaItem = reference,
                )
//                dateTime = active[0].value.toDate(ConverterDate.SIMPLE_DATE.formatter)
//                    ?.toString(ConverterDate.UTC2.formatter).orEmpty()
            }
        }
        val data = gson.toJsonTree(formSchema).toString()

        monitoringUseCase.updateActivityDetailSop(
            idBundle.subVesselId,
            UpdateActivitySopBodyPost(
                data = data,
                update = update,
                insert = insert,
                delete = delete,
                phaseActivityId = idBundle.phaseActivityId
            )
        ).setHandler(_updateDetailResponseSop).let { return@let disposable::add }
    }

    fun uploadPhoto(
        photo: File
    ) {
        monitoringUseCase.uploadPhoto(photo)
            .setHandler(_uploadPhotoResponse)
            .let { return@let disposable::add }
    }

    private fun updateActiveFormSchema(newActiveFormSchema: List<ActiveFormSchema>) {
        _activeSchema.value = ArrayList(newActiveFormSchema)
    }

    fun getValidationActivityDetail(subVesselId: String) {
        monitoringUseCase.getValidationActivityDetail(subVesselId)
            .setHandler(_validationActivityDetail)
            .let { return@let disposable::add }
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return disposable
    }
}
