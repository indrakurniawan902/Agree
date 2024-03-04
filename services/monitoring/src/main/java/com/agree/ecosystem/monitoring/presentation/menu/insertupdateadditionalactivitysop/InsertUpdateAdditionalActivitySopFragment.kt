package com.agree.ecosystem.monitoring.presentation.menu.insertupdateadditionalactivitysop

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import com.agree.ecosystem.monitoring.databinding.FragmentInsertAdditionalActivitySopBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.InsertActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ecosystem.monitoring.utils.MonitoringFormRuleImplementator
import com.agree.ui.data.reqres.model.FormItem
import com.agree.ui.domain.model.DynamicFormSchema
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.successSnackBar
import com.agree.ui.utils.DynamicFormUtils.setFormEnabled
import com.devbase.data.source.VmData
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.image.MultiplePhotoField
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.base.LgnTextField
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@Deprecated("Will be Rewrite")
class InsertUpdateAdditionalActivitySopFragment :
    AgrFormFragment<FragmentInsertAdditionalActivitySopBinding>(),
    InsertUpdateAdditionalActivityDataContract,
    ViewGroup.OnHierarchyChangeListener {

    var navParams: NavToAdditionalActivitySopParams? = null
    var idBundle: InsertActivitySopBundleParams? = null
    var isInsert: Boolean = false
    var formSchema: FormSchema? = null
    lateinit var formId: String
    lateinit var phaseName: String
    lateinit var order: String
    lateinit var date: String

    // Pair of tag and Dynamic form schema data
    var tagFormDataMap: MutableMap<Any, DynamicFormSchema> = mutableMapOf()

    override fun initData() {
        super.initData()
        activity?.intent?.let {
            navParams = it.getParcelableExtra(Constant.NAV_ADDITIONAL_ACTIVITY_PARAMS_BUNDLE)
        }

        navParams?.let {
            idBundle = it.idBundleParams
            phaseName = it.phaseName
            date = it.date
            order = it.order
            isInsert = it.isInsert
            formSchema = it.formSchema
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(InsertUpdateAdditionalActivityObserver(this, viewModel))
        if (!isInsert) {
            viewModel.getInsertFormSchemaData(
                params = AdditionalSopActivityDetailParams(
                    filter = "id='${idBundle?.phaseActivityId}'"
                )
            )
        } else {
            viewModel.changeToggleStatus()
            formSchema?.let {
                viewModel.insertReferenceSchema(it.formSchemaItem)
                viewModel.updateToRenderFormData(it.activeformSchema)
                viewModel.toRenderFormSchema.value?.let { formItemList ->
                    constructForm(formItemList.toList())
                }
            }
        }

        initForm()
    }

    override fun initUI() {
        super.initUI()
        val output: String = if (phaseName.length > 29) {
            phaseName.substring(
                0,
                29 - 1
            ) + ("...")
        } else {
            phaseName
        }
        binding.toolbar.text = output
        if (!isInsert) {
            binding.btnInsertUpdate.text = getString(R.string.action_update_record)
        }

        // Unknown Function?
        binding.formContainer.children.forEach {
            when (it) {
                is ViewGroup -> {
                    it.setOnHierarchyChangeListener(this)
                }
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
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            btnInsertUpdate.setOnClickListener {
                if (isValid()) {
                    DialogUtils.showCustomDialog(
                        context = requireContext(),
                        title = "",
                        message = getString(R.string.label_message_confirm_add_activity_sop),
                        positiveAction = Pair("Ya") { extractValueThenInsertOrUpdate() },
                        negativeAction = Pair("Batal", null)
                    )
                }
            }

            btnActivateUpdate.setOnClickListener {
                viewModel.changeToggleStatus()
            }
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun setFormEnabled(isEditable: Boolean) {
        super.setFormEnabled(isEditable)
        with(binding) {
            if (isEditable) {
                btnActivateUpdate.gone()
                btnInsertUpdate.visible()
            } else {
                btnActivateUpdate.visible()
                btnInsertUpdate.gone()
            }
            formContainer.getChildAsList().forEach {
                it.setFormEnabled(isEditable)
                when (it) {
                    is MultiplePhotoField -> {
                        viewModel.activeSchema.value?.let { activeFormSchema ->
                            val multiplePhotoField =
                                activeFormSchema.find { dynamicFormSchema -> it.tag == dynamicFormSchema.id }
                            multiplePhotoField?.let { dynamicFormSchema ->
                                if (dynamicFormSchema.value.isEmpty() && viewModel.toggleStatus.value == false) {
                                    it.gone()
                                } else {
                                    it.visible()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnInsertUpdate.isEnable = true
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

    override fun extractValueThenInsertOrUpdate() {
        val children = binding.formContainer.getChildAsList()
        var containImage = false
        children.forEach { view ->
            when (view) {
                is LgnTextField -> viewModel.updateActiveSchemaValueAt(
                    view.text,
                    view.tag.toString()
                )
                is LgnRadioContainer -> viewModel.updateActiveSchemaValueAt(
                    view.text,
                    view.tag.toString()
                )
                is LgnCheckBoxContainer -> viewModel.updateActiveSchemaValueAt(
                    view.text,
                    view.tag.toString()
                )
                is LgnChipContainer -> viewModel.updateActiveSchemaValueAt(
                    view.text,
                    view.tag.toString()
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
                                        btnInsertUpdate.isLoading = true
                                    }
                                }
                                is VmData.Success -> {
                                    imageUrls.add(it.data)
                                    if (imageUrls.size == images.size) {
                                        viewModel.updateActiveSchemaValueAt(
                                            imageUrls.joinToString(","),
                                            view.tag.toString()
                                        )
                                        if (isInsert) {
                                            insertAdditionalActivity()
                                        } else {
                                            updateAdditionalActivity()
                                        }
                                    }
                                }
                                is VmData.Failure -> {
                                    errorSnackBar(getString(R.string.label_error_photo_upload_snackbar))
                                }
                                else -> Unit
                            }
                        }
                    }
                }
            }
        }

        if (!containImage) {
            if (isInsert)
                insertAdditionalActivity()
            else
                updateAdditionalActivity()
        }
    }

    override fun insertAdditionalActivity() {
        idBundle?.let { bundleId ->
            viewModel.insertAdditionalActivity(
                idBundle = bundleId,
                userId = prefs.userId,
                activityName = phaseName,
                order = order,
                date = date
            )
        }
    }

    override fun updateAdditionalActivity() {
        viewModel.updateActivityDetail(
            id = formId,
            userId = prefs.userId,
            date = date
        )
    }

    override fun constructForm(formData: List<FormItem>) {
        super.constructForm(formData)
        val schema = formData.map { it.toDynamicFormSchema(viewModel.toggleStatus.value ?: false) }

        viewModel.toRenderFormSchema.value?.forEach { item ->
            tagFormDataMap[item.id] = item.toDynamicFormSchema(viewModel.toggleStatus.value ?: false)
        }

        with(binding) {
            formContainer.schema {
                addAndReplace(schema)
            }.build()
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
                            viewStatePair.value,
                            rule
                        )
                    }
            }
            is LgnCheckBoxContainer -> {
                ruleImplementator.implementRadioOrCheckBoxRule(form = viewStatePair.key)
                    ?.let { rule ->
                        (viewStatePair.key as LgnCheckBoxContainer).addRule(
                            viewStatePair.value,
                            rule
                        )
                    }
            }
        }
    }

    override fun setSubFormListener(schema: List<DynamicFormSchema>) {
        schema.forEach { schemaItem ->
            if (schemaItem.hasSubForm) {
                binding.formContainer.getDropDownFieldByTags(schemaItem.id)?.apply {
                    setListener { callback ->
                        val subForm = schemaItem
                            .subForm
                            .find { it.triggerValue == callback }
                            ?.displayedSchema ?: listOf()

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

    override fun onGetSopActivityDetailLoading() {
        super.onGetSopActivityDetailLoading()
    }

    override fun onGetSopActivityDetailSuccess(data: List<AdditionalSopActivityDetail>) {
        super.onGetSopActivityDetailSuccess(data)
        viewModel.insertReferenceSchema(data[0].formSchema.formSchemaItem)
        viewModel.updateToRenderFormData(data[0].formSchema.activeformSchema)
        order = data[0].order ?: "1"
        formId = data[0].id
        viewModel.toRenderFormSchema.value?.let {
            constructForm(it.toList())
        }
        initForm()
    }

    override fun onGetSopActivityDetailError(message: String?) {
        super.onGetSopActivityDetailError(message)
        errorSnackBar(message ?: "")
    }

    override fun onInsertAdditionalActivityLoading() {
        with(binding) {
            btnInsertUpdate.isLoading = true
        }
    }

    override fun onInsertAdditionalActivitySuccess(data: InsertActivitySop) {
        super.onInsertAdditionalActivitySuccess(data)
        with(binding) {
            btnInsertUpdate.isLoading = false
            formId = data.id
            order = data.order
        }
        isInsert = false
        successSnackBar(getString(R.string.label_success_insert_snackbar))
        viewModel.changeToggleStatus()
    }

    override fun onInsertAdditionalActivityError(e: Throwable?) {
        with(binding) {
            btnInsertUpdate.isLoading = false
        }
        errorSnackBar(getString(R.string.label_error_insert_snackbar))
    }

    override fun onUpdateAdditionalActivityLoading() {
        with(binding) {
            btnInsertUpdate.isLoading = true
        }
    }

    override fun onUpdateAdditionalActivitySuccess() {
        with(binding) {
            btnInsertUpdate.isLoading = false
        }
        successSnackBar(getString(R.string.label_success_update_snackbar))
        viewModel.changeToggleStatus()
    }

    override fun onUpdateAdditionalActivityError(e: Throwable?) {
        with(binding) {
            btnInsertUpdate.isLoading = false
        }
        errorSnackBar(getString(R.string.label_error_update_snackbar))
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

    private val viewModel: InsertUpdateAdditionalActivitySopViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
}
