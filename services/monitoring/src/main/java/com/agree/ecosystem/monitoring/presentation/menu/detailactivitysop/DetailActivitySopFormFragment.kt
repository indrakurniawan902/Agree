package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentDetailActivitySopFormBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.guidedialog.GuideDialogFragment
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual.IndividualMonitoringDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual.IndividualMonitoringObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual.IndividualMonitoringViewModel
import com.agree.ecosystem.monitoring.presentation.navigation.DetailActivitySopNavigation
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ecosystem.monitoring.utils.MonitoringFormRuleImplementator
import com.agree.ecosystem.monitoring.utils.MonitoringRecordType
import com.agree.ecosystem.monitoring.utils.MonitoringType
import com.agree.ui.data.reqres.model.FormItem
import com.agree.ui.domain.model.DynamicFormSchema
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.successSnackBar
import com.agree.ui.utils.DynamicFormUtils.setFormEnabled
import com.devbase.data.source.VmData
import com.devbase.data.utilities.rx.transformers.observableScheduler
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible
import com.jakewharton.rxbinding3.widget.textChanges
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.image.MultiplePhotoField
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.LgnSingleField
import com.telkom.legion.component.textfield.base.LgnTextField
import com.telkom.legion.component.utility.extension.findComponent
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DetailActivitySopFormFragment :
    AgrFormFragment<FragmentDetailActivitySopFormBinding>(),
    DetailActivitySopFormDataContract,
    IndividualMonitoringDataContract,
    ViewGroup.OnHierarchyChangeListener {

    var sopDetailData: SopActivityDetail? = null
    var idBundle: InsertActivitySopBundleParams? = null

    lateinit var formId: String
    lateinit var formOrder: String
    lateinit var guideContent: String
    lateinit var date: String
    lateinit var activityName: String

    private var individualId: String = ""
    private var type: String = String()
    private var individualData = listOf<IndividualSubVessel>()

    /**
     * For identify record type
     */
    var recordType: String = String()

    /**
     * For save updated data in Individual Monitoring
     */
    var updateIndividualSubVessel = mutableListOf<IndividualSubVessel>()

    /**
     * For save inserted and deleted data in Individual Monitoring
     */
    var insertIndividualSubVessel = mutableListOf<IndividualSubVessel>()

    /**
     * For save deleted data in Individual Monitoring
     */
    var deleteIndividualSubVessel = mutableListOf<IndividualSubVessel>()

    /**
     * Flag for checking if the form is in insert or edit mode
     */
    var isInsert: Boolean = false
    var isEdit: Boolean = false

    /**
     * Pair of tag and Dynamic form schema data for dynamic form
     */
    var tagFormDataMap: MutableMap<Any, DynamicFormSchema> = mutableMapOf()

    override fun initData() {
        super.initData()
        arguments?.let {
            activityName = it.getString(Constant.ACTIVITY_NAME_BUNDLE_KEY) ?: ""
            sopDetailData =
                it.getParcelable<SopActivityDetail>(Constant.SOP_DETAIL_KEY) as SopActivityDetail
            idBundle =
                it.getParcelable<InsertActivitySopBundleParams>(Constant.ID_BUNDLE_KEY) as InsertActivitySopBundleParams

            guideContent = it.getString(Constant.GUIDE_CONTENT_BUNDLE_KEY) ?: ""
            date = it.getString(Constant.DATE_BUNDLE_KEY) ?: ""
            individualId = it.getString(Constant.INDIVIDUAL_ID, "")
            recordType = it.getString(Constant.RECORD_TYPE_BUNDLE_KEY, "")
            type = it.getString(Constant.TYPE_BUNDLE_KEY, "")
            isEdit = it.getBoolean(Constant.IS_EDIT_BUNDLE_KEY, false)
        }

        sopDetailData?.let {
            viewModel.insertReferenceSchema(it.formSchema.formSchemaItem)
            viewModel.updateToRenderFormData(it.formSchema.activeformSchema)
            formId = it.id
            formOrder = it.order ?: "1"
            isInsert = it.isInsert
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            formContainer.children.forEach {
                when (it) {
                    is ViewGroup -> {
                        it.setOnHierarchyChangeListener(this@DetailActivitySopFormFragment)
                    }
                }
            }

            if (isInsert) GuideDialogFragment(activityName, guideContent, date).showNow(
                childFragmentManager, "dialog"
            )

            viewModel.toRenderFormSchema.value?.let {
                constructForm(it.toList())
            }

            if (recordType == MonitoringRecordType.INDIVIDUAL.value && individualId.isEmpty()) {
                if (isInsert) {
                    containerIndividual.gone()
                    btnUpdate.text = getString(R.string.action_save)
                } else {
                    containerIndividual.visible()
                }
            } else containerIndividual.gone()
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            DetailActivitySopFormObserver(this, viewModel),
            IndividualMonitoringObserver(this, individualViewModel)
        )

        /**
         * If isInsert is true, then we need to change the toggle status
         */
        if (isInsert) {
            viewModel.changeToogleStatus()
        }

        viewModel.toRenderFormSchema.value?.forEach {
            tagFormDataMap[it.id] = it.toDynamicFormSchema(viewModel.toggleStatus.value ?: false)
        }

        initForm()
    }

    private fun makeDelay() {
        lifecycleScope.launch {
            delay(2000)
            viewModel.changeToogleStatus()
        }
    }

    override fun initAction() {
        super.initAction()
        if (recordType == MonitoringRecordType.INDIVIDUAL.value && !isInsert) {
            if (type == MonitoringType.ENTRY_POINT.value) fetchIndividualMonitoring()
            makeDelay()
        }
        with(binding) {
            btnActivateUpdate.setOnClickListener {
                viewModel.changeToogleStatus()
            }

            btnUpdate.setOnClickListener {
                if (isValid()) {
                    DialogUtils.showCustomDialog(
                        context = requireContext(),
                        title = "",
                        message = getString(R.string.label_message_confirm_update_sop),
                        positiveAction = Pair("Ya") { extractValueThenUpdateOrInsert() },
                        negativeAction = Pair("Batal", null)
                    )
                }
            }

            tvShowGuide.setOnClickListener {
                GuideDialogFragment(activityName, guideContent, date).showNow(
                    childFragmentManager, "dialog"
                )
            }
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnUpdate.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        when (view) {
            is LgnTextField -> {
                view.error = errorMessage
            }

            is LgnRadioContainer -> {
                view.error = errorMessage
            }

            is LgnCheckBoxContainer -> {
                view.error = errorMessage
            }

            is LgnChipContainer -> {
                view.error = errorMessage
            }
        }
    }

    override fun onFormValidated(view: View) {
        when (view) {
            is LgnTextField -> {
                view.error = ""
            }

            is LgnRadioContainer -> {
                view.error = ""
            }

            is LgnCheckBoxContainer -> {
                view.error = ""
            }

            is LgnChipContainer -> {
                view.error = ""
            }
        }
    }

    override fun initForm() {
        val viewStateMap: MutableMap<View, MutableStateFlow<ValidationModel>> = mutableMapOf()
        val ruleImplementator = MonitoringFormRuleImplementator(tagFormDataMap)

        binding.formContainer.getChildAsList().forEach {
            viewStateMap[it] = it.initState()
        }

        registerFormState(
            *viewStateMap.values.toTypedArray()
        )

        viewStateMap.forEach {
            addValidationRule(it, ruleImplementator)
        }

        referenceTextChanged()
    }

    @SuppressLint("CheckResult")
    private fun referenceTextChanged() {
        val activeSchemas = viewModel.activeSchema.value?.toList().orEmpty()
        binding.formContainer.getChildAsList().forEach { view ->
            when (view) {
                is LgnTextField -> {
                    val activeSchema =
                        activeSchemas.find { activeSchema -> activeSchema.id == view.tag }

                    if (activeSchema?.is_reference == "true") {
                        /**
                         * Listener to display how many form inputs to display
                         */
                        view.editText?.textChanges()?.skipInitialValue()
                            ?.debounce(500, TimeUnit.MILLISECONDS)?.compose(observableScheduler())
                            ?.subscribe {
                                runCatching {
                                    updateDataListIndividual(
                                        if (it.isNullOrEmpty()) 0 else it.toString().toInt()
                                    )
                                }
                            }
                    }
                }
            }
        }
    }

    /**
     * Add Listener for Individual Form and will save in [updateIndividualSubVessel]
     */
    private fun addIndividualFormListener() {
        with(binding) {
            containerIndividualForm.children
                .findComponent(LgnSingleField::class)
                .onEach { view ->
                    view.editText?.textChanges()?.skipInitialValue()
                        ?.debounce(800, TimeUnit.MILLISECONDS)?.compose(observableScheduler())
                        ?.subscribe {
                            runCatching {
                                val existingItem =
                                    updateIndividualSubVessel.find { it.id == view.tag }

                                if (existingItem != null) {
                                    updateIndividualSubVessel.find { it.id == view.tag }?.apply {
                                        code = it.toString()
                                    }
                                } else {
                                    updateIndividualSubVessel.add(
                                        IndividualSubVessel(
                                            view.tag.toString(),
                                            it.toString()
                                        )
                                    )
                                }

                                insertIndividualSubVessel.find { it.id == view.tag.toString() }
                                    ?.apply {
                                        code = it.toString()
                                    }
                            }
                        }?.let {
                            viewModel.getCompositeDisposable().add(
                                it
                            )
                        }
                }
        }
    }

    /**
     * Update Data List Individual by count
     */
    private fun updateDataListIndividual(count: Int) {
        if (count < 1) {
            return
        }
        with(binding) {
            msvContentIndividual.showDefaultLayout()
            deleteIndividualSubVessel.clear()
            insertIndividualSubVessel.clear()
            if (count > containerIndividualForm.childCount) {
                val newListData = mutableListOf<IndividualSubVessel>()
                if (count < individualData.size) {
                    individualData.forEachIndexed { index, individualSubVessel ->
                        if (index >= count)
                            deleteIndividualSubVessel.add(individualSubVessel)
                        else if (containerIndividualForm.childCount <= index)
                            newListData.add(individualSubVessel)
                    }
                } else {
                    for (i in containerIndividualForm.childCount until count) {
                        if (i < individualData.size) {
                            val oldData = individualData[i]
                            newListData.add(oldData)
                        } else newListData.add(newIndividualSubVessel(i))
                    }

                    // Add New IndividualSubVessel
                    for (i in individualData.size until count) {
                        insertIndividualSubVessel.add(newIndividualSubVessel(i))
                    }
                }

                newListData.forEach { data ->
                    val placeholderText = "ID ".plus(containerIndividualForm.childCount + 1)
                    containerIndividualForm.addView(
                        LgnSingleField(requireContext()).apply {
                            id = View.generateViewId()
                            placeHolder = placeholderText
                            hint = placeholderText
                            isRequired = true
                            text = data.code.trim()
                            tag = data.id.trim()
                        }
                    )
                }
            } else {
                if (containerIndividualForm.childCount > individualData.size) {
                    for (i in containerIndividualForm.childCount - 1 downTo count) {
                        removeFromListDataUpdate(i)
                        containerIndividualForm.removeViewAt(i)
                        if (i < individualData.size) {
                            deleteIndividualSubVessel.add(individualData[i])
                        }
                    }

                    // Add New IndividualSubVessel
                    for (i in individualData.size until count) {
                        insertIndividualSubVessel.add(newIndividualSubVessel(i))
                    }
                } else {
                    for (i in individualData.size - 1 downTo count) {
                        removeFromListDataUpdate(i)
                        if (i < containerIndividualForm.childCount) {
                            containerIndividualForm.removeViewAt(i)
                        }
                        deleteIndividualSubVessel.add(individualData[i])
                    }
                }
            }
            addIndividualFormListener()
        }
    }

    /**
     * Default New Data [IndividualSubVessel]
     */
    private fun newIndividualSubVessel(index: Int): IndividualSubVessel {
        val newIndex = index + 1
        val sequence = "$newIndex".padStart(4, '0')
        val code = "ID $sequence"
        return IndividualSubVessel(newIndex.toString(), code)
    }

    /**
     * Remove [IndividualSubVessel] from List Data Update [updateIndividualSubVessel]
     */
    private fun removeFromListDataUpdate(index: Int) {
        if (index < individualData.size) {
            if (updateIndividualSubVessel.any { it.id == individualData[index].id }) {
                val indexUpdateRemoved =
                    updateIndividualSubVessel.indexOf(updateIndividualSubVessel.find { it.id == individualData[index].id })
                if (indexUpdateRemoved != -1)
                    updateIndividualSubVessel.removeAt(indexUpdateRemoved)
            }
        } else {
            if (updateIndividualSubVessel.any { it.id == (index + 1).toString() }) {
                val indexUpdateRemoved =
                    updateIndividualSubVessel.indexOf(updateIndividualSubVessel.find { it.id == (index + 1).toString() })
                if (indexUpdateRemoved != -1)
                    updateIndividualSubVessel.removeAt(indexUpdateRemoved)
            }
        }
    }

    override fun addValidationRule(
        viewStatePair: Map.Entry<View, MutableStateFlow<ValidationModel>>,
        ruleImplementator: MonitoringFormRuleImplementator
    ) {
        super.addValidationRule(viewStatePair, ruleImplementator)
        when (viewStatePair.key) {
            is LgnTextField -> {
                (viewStatePair.key as LgnTextField).addRule(
                    viewStatePair.value,
                    *ruleImplementator.implementTextFieldRule(
                        form = viewStatePair.key
                    )
                )
            }

            is LgnRadioContainer -> {
                ruleImplementator.implementRadioOrCheckBoxRule(form = viewStatePair.key)
                    ?.let { rule ->
                        (viewStatePair.key as LgnRadioContainer).addRule(
                            viewStatePair.value, rule
                        )
                    }
            }

            is LgnCheckBoxContainer -> {
                ruleImplementator.implementRadioOrCheckBoxRule(form = viewStatePair.key)
                    ?.let { rule ->
                        (viewStatePair.key as LgnCheckBoxContainer).addRule(
                            viewStatePair.value, rule
                        )
                    }
            }
        }
    }

    override fun onUpdateSopActivityDetailLoading() {
        super.onUpdateSopActivityDetailLoading()
        with(binding) {
            btnUpdate.isLoading = true
        }
    }

    override fun onUpdateSopActivityDetailSuccess() {
        super.onUpdateSopActivityDetailSuccess()
        with(binding) {
            btnUpdate.isLoading = false
        }
        viewModel.changeToogleStatus()
        successSnackBar(getString(R.string.label_success_insert_snackbar))
    }

    override fun onUpdateSopActivityDetailError(e: Throwable?) {
        super.onUpdateSopActivityDetailError(e)
        with(binding) {
            btnUpdate.isLoading = false
        }
    }

    override fun onInsertSopActivityDetailLoading() {
        super.onInsertSopActivityDetailLoading()
        with(binding) {
            btnUpdate.isLoading = true
        }
    }

    override fun onInsertSopActivityDetailSuccess() {
        super.onInsertSopActivityDetailSuccess()
        binding.btnUpdate.isLoading = false
        viewModel.changeToogleStatus()
        successSnackBar("Berhasil merubah data")

        if (recordType == MonitoringRecordType.INDIVIDUAL.value && isInsert) {
            if (individualId.isNotEmpty()) {
                val formItems = viewModel.referenceSchema.value?.toList().orEmpty()
                val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(date.split('T')[0], dtf)
                val defaultZoneId: ZoneId = ZoneId.systemDefault()
                val formattedStringDate =
                    SimpleDateFormat("EEEE, dd MMM yyyy", Locale("id")).format(
                        Date.from(
                            localDate.atStartOfDay(
                                defaultZoneId
                            )?.toInstant()
                        )
                    )
                viewModel.activeSchema.value?.forEach { data ->
                    formItems.find { it.id == data.id }?.apply {
                        value = data.value
                    }
                    formItems.find { it.id == "ID01" }?.apply { value = formattedStringDate }
                }
                detailActivitySopNav.fromDetailActivitySopToActivitySummary(
                    activityName, formItems
                )
            } else {
                detailActivitySopNav.fromDetailActivitySopToIndividualMonitoring(
                    subVesselId = idBundle?.subVesselId.toString(),
                    type = type,
                    activityName = activityName,
                    guideContent = guideContent,
                    date = date
                )
            }
        }
        isInsert = false
    }

    override fun onInsertSopActivityDetailError(e: Throwable?) {
        super.onInsertSopActivityDetailError(e)
        errorSnackBar(getString(R.string.label_error_insert_snackbar))
        binding.btnUpdate.isLoading = false
    }

    override fun extractValueThenUpdateOrInsert() {
        super.extractValueThenUpdateOrInsert()

        val children = binding.formContainer.getChildAsList()
        var containImage = false
        children.forEach { view ->
            when (view) {
                is LgnTextField -> viewModel.updateActiveSchemaValueAt(
                    view.text, view.tag.toString()
                )

                is LgnRadioContainer -> viewModel.updateActiveSchemaValueAt(
                    view.text, view.tag.toString()
                )

                is LgnCheckBoxContainer -> viewModel.updateActiveSchemaValueAt(
                    view.text, view.tag.toString()
                )

                is LgnChipContainer -> viewModel.updateActiveSchemaValueAt(
                    view.text, view.tag.toString()
                )

                is MultiplePhotoField -> {
                    val images = view.images
                    val imageUrls = mutableListOf<String>()
                    if (images.isNotEmpty()) {
                        containImage = true
                        images.forEach { image ->
                            viewModel.uploadPhoto(image)
                        }
                        viewModel.uploadPhotoResponse.observe(this) {
                            when (it) {
                                is VmData.Loading -> {
                                    with(binding) {
                                        btnUpdate.isLoading = true
                                    }
                                }

                                is VmData.Success -> {
                                    imageUrls.add(it.data)
                                    if (imageUrls.size == images.size) {
                                        viewModel.updateActiveSchemaValueAt(
                                            imageUrls.joinToString(","), view.tag.toString()
                                        )
                                        if (isInsert) {
                                            insertSopActivityDetailData()
                                        } else {
                                            updateSopActivityDetailData()
                                        }
                                    }
                                }

                                is VmData.Failure -> {
                                    errorSnackBar(getString(R.string.label_error_photo_upload_snackbar))
                                }

                                else -> Unit
                            }
                        }
                    } else viewModel.updateActiveSchemaValueAt(String(), view.tag.toString())
                }
            }
        }

        if (!containImage) {
            if (isInsert) {
                insertSopActivityDetailData()
            } else {
                updateSopActivityDetailData()
            }
        }
    }

    override fun updateSopActivityDetailData() {
        super.updateSopActivityDetailData()
        if (recordType == MonitoringRecordType.INDIVIDUAL.value) {
            updateIndividualSubVessel =
                updateIndividualSubVessel.filter { data -> updateIndividualSubVessel.count { it.id == data.id } == 1 }
                    .toMutableList()

            sopDetailData?.let { sopDetail ->
                idBundle?.let { bundleId ->
                    viewModel.updateActivityDetailSop(
                        idBundle = bundleId,
                        date = date,
                        update = updateIndividualSubVessel,
                        insert = insertIndividualSubVessel,
                        delete = deleteIndividualSubVessel
                    )
                }
            }
        } else
            viewModel.updateActivityDetail(
                id = formId, userId = prefs.userId, date = date
            )
    }

    override fun insertSopActivityDetailData() {
        super.insertSopActivityDetailData()

        sopDetailData?.let { sopDetail ->
            idBundle?.let { bundleId ->
                viewModel.insertActivityDetail(
                    idBundle = bundleId,
                    userId = prefs.userId,
                    activityName = sopDetail.name,
                    order = sopDetail.order ?: "1",
                    date = date,
                    individualId = individualId
                )
            }
        }
    }

    override fun setFormEnabled(isEditable: Boolean) {
        super.setFormEnabled(isEditable)
        with(binding) {
            if (isEditable) {
                btnActivateUpdate.gone()
                btnUpdate.visible()
                tvShowGuide.visible()
            } else {
                btnActivateUpdate.visible()
                btnUpdate.gone()
                tvShowGuide.gone()
            }
            val isFormEditable =
                if (recordType == MonitoringRecordType.INDIVIDUAL.value && type == MonitoringType.ENTRY_POINT.value && !isInsert) isEdit
                else isEditable
            formContainer.getChildAsList().onEach {
                it.setFormEnabled(isFormEditable)
            }
        }
    }

    override fun constructForm(formData: List<FormItem>) {
        super.constructForm(formData)
        val toggleStatus = viewModel.toggleStatus.value ?: false
        val schema = formData.map { it.toDynamicFormSchema(toggleStatus) }
        with(binding) {
            formContainer.schema {
                addAndReplace(schema)
            }.build()
//            lifecycleScope.launch {
//                formContainer.getChildAsList().forEach {
//                    it.setFormEnabled(viewModel.toggleStatus.value ?: false)
//                }
//            }
        }
    }

    override fun isValid(): Boolean {
        var valid = true
        var formValidity = false
        with(binding) {
            formContainer.getChildAsList().forEach {
                it.state?.let { viewState ->
                    if (!viewState.value.isValid) {
                        when (viewState.value.view) {
                            is LgnTextField -> {
                                if ((viewState.value.view as LgnTextField).isRequired) {
                                    onFormUnvalidated(
                                        viewState.value.view,
                                        getString(R.string.not_empty_default_error)
                                    )
                                    formValidity = false
                                } else if ((viewState.value.view as LgnTextField).text.isEmpty()) {
                                    formValidity = true
                                }
                            }

                            is LgnCheckBoxContainer -> {
                                if ((viewState.value.view as LgnCheckBoxContainer).isRequired) {
                                    onFormUnvalidated(
                                        viewState.value.view,
                                        getString(R.string.not_checked_default_error)
                                    )
                                    formValidity = false
                                } else if ((viewState.value.view as LgnCheckBoxContainer).checkBoxGroup.text.isEmpty()) {
                                    formValidity = true
                                }
                            }

                            is LgnRadioContainer -> {
                                if ((viewState.value.view as LgnRadioContainer).isRequired) {
                                    onFormUnvalidated(
                                        viewState.value.view,
                                        getString(R.string.not_checked_default_error)
                                    )
                                    formValidity = false
                                } else if ((viewState.value.view as LgnRadioContainer).radioGroup.text.isEmpty()) {
                                    formValidity = true
                                }
                            }

                            is LgnChipContainer -> {
                                if ((viewState.value.view as LgnChipContainer).isRequired) {
                                    onFormUnvalidated(
                                        viewState.value.view,
                                        getString(R.string.not_checked_default_error)
                                    )
                                    formValidity = false
                                } else if ((viewState.value.view as LgnChipContainer).chipGroup?.text?.isEmpty() == true) {
                                    formValidity = true
                                }
                            }

                            else -> {
                                formValidity = true
                            }
                        }
                    }
                    valid =
                        if (formValidity) valid && formValidity else valid && viewState.value.isValid
                }
            }
        }
        return valid
    }

    override fun setSubFormListener(schema: List<DynamicFormSchema>) {
        schema.forEach { schemaItem ->
            if (schemaItem.hasSubForm) {
                binding.formContainer.getDropDownFieldByTags(schemaItem.id)?.apply {
                    setListener { callback ->
                        val subForm =
                            schemaItem.subForm.find { it.triggerValue == callback }?.displayedSchema
                                ?: listOf()

                        viewModel.displayedSchemaToActiveSchema(subForm)

                        viewModel.activeSchema.value?.let {
                            viewModel.updateToRenderFormData(it)
                        }

                        binding.formContainer.schema {
                            removeAtBellow(schemaItem.id)
                        }.update()

                        binding.formContainer.schema {
                            viewModel.toRenderFormSchema.value?.let { activeFormData ->
                                addAndReplace(
                                    activeFormData.map {
                                        it.toDynamicFormSchema(
                                            viewModel.toggleStatus.value ?: false
                                        )
                                    }
                                )
                            }
                        }.update()

                        viewModel.toRenderFormSchema.value?.forEach {
                            tagFormDataMap[it.id] =
                                it.toDynamicFormSchema(viewModel.toggleStatus.value ?: false)
                        }
                        initForm()
                    }
                }
            }
        }
    }

    /**
     * We don't use paging
     * If we use paging, we can't get the data list from below (last) to delete and send to Backend
     * Based on information from Project Owner, the maximum list data individual is 200
     * So we set it (pageSize) to 200
     */
    override fun fetchIndividualMonitoring() {
        individualViewModel.getIndividualMonitoring(
            subVesselId = idBundle?.subVesselId.toString(),
            userId = prefs.userId,
            pageSize = 200
        )
    }

    override fun onIndividualLoading() {
        binding.msvContentIndividual.showLoadingLayout()
    }

    override fun onIndividualSuccess(data: List<IndividualSubVessel>) {
        with(binding) {
            msvContentIndividual.showDefaultLayout()
            individualData = data
            containerIndividualForm.removeAllViews()
            data.forEachIndexed { index, data ->
                val placeholderText = "ID ".plus(index + 1)
                containerIndividualForm.addView(
                    LgnSingleField(requireContext()).apply {
                        id = View.generateViewId()
                        placeHolder = placeholderText
                        hint = placeholderText
                        isRequired = true
                        text = data.code.trim()
                        tag = data.id.trim()
                    }
                )
            }

            /**
             * Add listener to individual monitoring form
             */
            addIndividualFormListener()

            updateIndividualSubVessel.clear()
            insertIndividualSubVessel.clear()
            insertIndividualSubVessel.clear()
        }
    }

    override fun onIndividualEmpty() {
        binding.msvContentIndividual.showEmptyLayout()
    }

    override fun onIndividualError(e: Throwable?) {
        binding.msvContentIndividual.showErrorLayout()
    }

    override fun onChildViewAdded(p0: View?, p1: View?) {
        viewModel.toRenderFormSchema.value?.let { schema ->
            setSubFormListener(
                schema.map {
                    it.toDynamicFormSchema(
                        viewModel.toggleStatus.value ?: false
                    )
                }
            )
        }
    }

    override fun onChildViewRemoved(p0: View?, p1: View?) {
        // Do Nothing
    }

    private val viewModel: DetailActivitySopViewModel by viewModel()
    private val individualViewModel: IndividualMonitoringViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
    private val detailActivitySopNav: DetailActivitySopNavigation by navigation { activity }

    override fun onUpdateActivityDetailSopSuccess() {
        super.onUpdateActivityDetailSopSuccess()
        binding.btnUpdate.isLoading = false
        if (recordType == MonitoringRecordType.INDIVIDUAL.value)
            fetchIndividualMonitoring()
        else viewModel.changeToogleStatus()
        successSnackBar(getString(R.string.label_success_insert_snackbar))
    }

    override fun onUpdateActivityDetailSopLoading() {
        super.onUpdateActivityDetailSopLoading()
        // do nothing
    }
}
