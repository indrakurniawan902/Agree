package com.agree.ecosystem.monitoring.presentation.menu.additionalactivities

import android.view.View
import com.agree.ecosystem.core.utils.base.AgrFormFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentAdditionalActivitiesBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.utils.Constant
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.base.LgnTextField
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdditionalActivitiesFragment :
    AgrFormFragment<FragmentAdditionalActivitiesBinding>(),
    AdditionalActivitiesDataContract {

    var navParams: NavToAdditionalActivitySopParams? = null
    var isInsert: Boolean = false

    override fun initData() {
        super.initData()
        navParams =
            requireActivity().intent.getParcelableExtra(Constant.NAV_ADDITIONAL_ACTIVITY_PARAMS_BUNDLE)
        isInsert = requireNotNull(navParams).isInsert
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            val output: String = if ((navParams?.phaseName?.length ?: 0) > 29) {
                navParams?.phaseName?.substring(
                    0,
                    29 - 1
                ) + ("...")
            } else {
                navParams?.phaseName.orEmpty()
            }
            toolbar.text = output

            if (!isInsert) {
                btnInsertUpdate.text = getString(R.string.action_update_record)
            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(AdditionalActivitiesObserver(this, viewModel))

        if (isInsert) {
            /**
             * Condition if form request is Insert
             */
            viewModel.toggleStatus()
        }
    }

    override fun setFormEnabled(isEditable: Boolean) {
        with(binding) {
            if (isEditable) {
                btnActivateUpdate.gone()
                btnInsertUpdate.visible()
            } else {
                btnActivateUpdate.visible()
                btnInsertUpdate.gone()
            }
        }
    }

    override fun insertAdditionalActivity() {
    }

    override fun updateAdditionalActivity() {
    }

    override fun initForm() {
    }

    override fun onAllFormValidated() {
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

    private val viewModel: AdditionalActivitiesViewModel by viewModel()
}
