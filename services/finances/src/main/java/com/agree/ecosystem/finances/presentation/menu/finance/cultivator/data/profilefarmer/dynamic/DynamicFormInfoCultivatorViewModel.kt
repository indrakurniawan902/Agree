package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.dynamic

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FormFieldSchema
import com.agree.ui.data.reqres.model.FormItem
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DynamicFormInfoCultivatorViewModel(private val useCase: FinanceUsecase) : DevViewModel() {

    private val _toRenderFormSchema = MutableLiveData<ArrayList<FormItem>>(ArrayList())
    val toRenderFormSchema = _toRenderFormSchema.immutable()

    private val _resultDataCultivator = DevData<Map<String, FormFieldSchema>>().apply { default() }
    val resultDataCultivator get() = _resultDataCultivator

    private val _toggleStatus = MutableLiveData<Boolean>(false)
    val toggleStatus = _toggleStatus.immutable()

//    private val _insertDetailResponse = DevData<InsertActivitySop>().apply { default() }
//    val insertDetailResponse = _insertDetailResponse.immutable()
//
//    private val _updateDetailResponse = DevData<SopActivityDetailBodyPost>().apply { default() }
//    val updateDetailResponse = _updateDetailResponse.immutable()

    private val _uploadPhotoResponse = DevData<String>().apply { default() }
    val uploadPhotoResponse = _uploadPhotoResponse.immutable()

//    private val _formSchemaData = DevData<List<AdditionalSopActivityDetail>>().apply { default() }
//    val formSchemaData = _formSchemaData.immutable()
//
//    private val _activeSchema = MutableLiveData<ArrayList<ActiveFormSchema>>(ArrayList())
//    val activeSchema = _activeSchema.immutable()

    private val _referenceSchema = MutableLiveData<ArrayList<FormItem>>(ArrayList())
    val referenceSchema = _referenceSchema.immutable()

    fun fetchData(
        borrowerId: String,
        schemeName: String
    ) {
        useCase.fetchDynamicFormCultivatorProfileData(
            borrowerId, schemeName
        )
            .setHandler(_resultDataCultivator)
            .let { return@let disposable::add }
    }

    /*
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

     */
}
