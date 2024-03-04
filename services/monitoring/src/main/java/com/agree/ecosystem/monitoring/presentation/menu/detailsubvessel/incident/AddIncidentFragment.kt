package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import android.annotation.SuppressLint
import android.view.View
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.rules.emptyIgnore
import com.agree.ecosystem.core.utils.utility.validation.validation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.data.reqres.model.incident.AddIncidentBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.incident.ImageArrayBodyPost
import com.agree.ecosystem.monitoring.databinding.FragmentAddIncidentBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.category.IncidentCategory
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ui.snackbar.successSnackBar
import com.devbase.presentation.validation.util.notEmptyRule
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.tools.*
import com.telkom.legion.component.textfield.base.LgnTextField
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddIncidentFragment :
    AgrFormFragment<FragmentAddIncidentBinding>(), AddIncidentDataContract {

    private val dateValidation by validation { etDateIncident }
    private val categoryValidation by validation { etCategoryIncident }
    private val incidentValidation by validation { etIncident }
    private val effectedValidation by validation { etEffectedPopulation }
    private val effortValidation by validation { etEffortToDo }
    private var incidentData = arrayListOf<IncidentCategory>()
    private var incidentRequired = true
    private var categoryRequired = true
    private var isClicked = false

    override fun initObserver() {
        super.initObserver()
        addObservers(AddIncidentObserver(this, viewModel))
        validation.registerValidations(
            dateValidation,
            categoryValidation,
            incidentValidation,
            effectedValidation,
            effortValidation
        )
        sharedVm.observeState()
    }

    @SuppressLint("SimpleDateFormat")
    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            btnAddIncident.setOnClickListener {
                isClicked = true
                if (isValid()) {
                    val photos = arrayListOf<ImageArrayBodyPost>()
                    multiplePhoto.images.forEach {
                        photos.add(ImageArrayBodyPost(it))
                    }
                    val parser = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val selectedDate: Date? = parser.parse(etDateIncident.text)
                    val dateFinal = selectedDate.let { formatter.format(it) }
                    val body = AddIncidentBodyPost(
                        dateFinal, sharedVm.getSubVesselId(), etCategoryIncident.text,
                        etIncident.text, etEffectedPopulation.text, etEffortToDo.text,
                        etCost.text, etNotes.text
                    )
                    val images = arrayListOf<MultipartBody.Part>()

                    photos.forEach {
                        images.add(
                            MultipartBody.Part.createFormData(
                                "images", it.file.name, it.file.asRequestBody("image/*".toMediaTypeOrNull())
                            )
                        )
                    }

                    viewModel.addIncident(body, images)
                }
            }

            etCategoryIncident.setListener {
                incidentData.forEach { incident ->
                    etIncident.text = ""
                    if (it.trim().split("\\s".toRegex()).joinToString(" ") { it.replaceFirstChar { it.lowercase() } } == incident.name) {
                        if (incident.incident.isNotEmpty()) {
                            etIncident.visible()
                            etIncident.addAll(incident.incident)
                            incidentRequired = true
                            initForm()
                        } else {
                            etIncident.gone()
                            incidentRequired = false
                        }
                    }
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        viewModel.getListCategory(
            sharedVm.getCompanyId(),
            sharedVm.getCommodityId()
        )
    }

    override fun onAllFormValidated() {
        with(binding) {
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            if (view is LgnTextField && isClicked) {
                view.error = errorMessage
            }
        }
    }

    override fun onFormValidated(view: View) {
        if (view is LgnTextField) view.error = ""
    }

    override fun isValid(): Boolean {
        with(binding) {
            var isAllValid = true
            if (!dateValidation.value.isValid) onFormUnvalidated(
                dateValidation.value.view,
                if (dateValidation.value.errorMessage == "") getString(
                    R.string.label_date_incident_required
                ) else dateValidation.value.errorMessage
            )
            isAllValid = dateValidation.value.isValid && isAllValid

            if (!categoryValidation.value.isValid) onFormUnvalidated(
                categoryValidation.value.view,
                if (categoryValidation.value.errorMessage == "") getString(
                    R.string.label_category_incident_required
                ) else categoryValidation.value.errorMessage
            )

            if (categoryRequired) {
                isAllValid = categoryValidation.value.isValid && isAllValid
            }

            if (!incidentValidation.value.isValid) onFormUnvalidated(
                incidentValidation.value.view,
                if (incidentValidation.value.errorMessage == "") getString(
                    R.string.label_incident_required
                ) else incidentValidation.value.errorMessage
            )

            if (incidentRequired) {
                isAllValid = incidentValidation.value.isValid && isAllValid
            }

            if (!effectedValidation.value.isValid) onFormUnvalidated(
                effectedValidation.value.view,
                if (effectedValidation.value.errorMessage == "") getString(
                    R.string.label_incident_affected_required
                ) else effectedValidation.value.errorMessage
            )
            isAllValid = effectedValidation.value.isValid && isAllValid

            if (!effortValidation.value.isValid) onFormUnvalidated(
                effortValidation.value.view,
                if (effortValidation.value.errorMessage == "") getString(
                    R.string.label_effort_required
                ) else effortValidation.value.errorMessage
            )
            isAllValid = effortValidation.value.isValid && isAllValid

            return isAllValid
        }
    }

    override fun initForm() {
        with(binding) {
            etDateIncident.addRule(
                dateValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(
                            R.string.label_date_incident
                        )
                    )
                )
            )
            if (categoryRequired) {
                etCategoryIncident.addRule(
                    categoryValidation,
                    notEmptyRule(
                        getString(
                            R.string.error_empty_field,
                            getStringResource(
                                R.string.label_category_incident
                            )
                        )
                    )
                )
            }

            if (incidentRequired) {
                etIncident.addRule(
                    incidentValidation,
                    notEmptyRule(
                        getString(
                            R.string.error_empty_field,
                            getStringResource(
                                R.string.label_incident
                            )
                        )
                    )
                )
            }

            etEffectedPopulation.addRule(
                effectedValidation,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(
                            R.string.label_effected
                        )
                    )
                )
            )
            etEffortToDo.addRule(
                effortValidation,
                emptyIgnore(
                    getString(
                        R.string.error_empty_field,
                        getStringResource(
                            R.string.label_effort
                        )
                    )
                )
            )
        }
    }

    private val viewModel: AddIncidentViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()

    override fun onAddIncidentLoading() {
        binding.btnAddIncident.isLoading = true
    }

    override fun onAddIncidentSuccess() {
        activity?.onBackPressed()
        binding.btnAddIncident.isLoading = false
        successSnackBar(getStringResource(R.string.label_success_add_incident))
    }

    override fun onAddIncidentFailed(e: Throwable?) {
        super.onAddIncidentFailed(e)
        binding.btnAddIncident.isLoading = false
        toast(e?.message.toString())
    }

    override fun onGetIncidentCategoriesLoading() {
        binding.etCategoryIncident.disable()
    }

    override fun onGetIncidentCategoriesSuccess(data: List<IncidentCategory>) {
        with(binding) {
            initForm()
            categoryRequired = true
            etCategoryIncident.visible()
            etCategoryIncident.enable()
            val categoryIncident = arrayListOf<String>()
            incidentData.clear()
            data.forEach {
                incidentData.add(it)
                val newName = it.name.trim().split("\\s".toRegex()).joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
                categoryIncident.add(newName)
            }
            etCategoryIncident.addAll(
                categoryIncident
            )
        }
    }

    override fun onGetIncidentCategoriesFailed(e: Throwable?) {
        super.onGetIncidentCategoriesFailed(e)
        incidentRequired = false
        categoryRequired = false
        binding.etCategoryIncident.gone()
    }
}
