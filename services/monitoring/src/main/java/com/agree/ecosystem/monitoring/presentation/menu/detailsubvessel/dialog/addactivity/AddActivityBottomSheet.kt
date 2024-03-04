package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrFormBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.validation.addRule
import com.agree.ecosystem.core.utils.utility.validation.initState
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.InsertSopDateBodyPost
import com.agree.ecosystem.monitoring.databinding.LayoutAddActivityBottomSheetBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ui.snackbar.successSnackBar
import com.devbase.presentation.validation.util.notEmptyRule
import com.google.android.material.snackbar.Snackbar
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding
import com.oratakashi.viewbinding.core.tools.gone
import com.telkom.legion.component.textfield.base.LgnTextField
import com.telkom.legion.extension.snackbar.LgnSnackBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddActivityBottomSheet(
    private val detailSubVessel: DetailSubVessel,
    private val callback: () -> Unit
) : AgrFormBottomSheet(), AddActivityDataContract {
    private val binding: LayoutAddActivityBottomSheetBinding by viewBinding()
    private val viewModel: AddActivityViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.etCalendar.placeHolder =
            getString(R.string.placeholder_add_date, detailSubVessel.activityName)
        // binding.etCalendar.hint = getString(R.string.hint_add_date, detailSubVessel.activityName)
        binding.tvDetailActivityName.gone()
        binding.tvTitle.text =
            getString(R.string.label_livestock_activation, sharedVm.getSubVesselName())
        binding.tvDescDetailActivityName.text =
            getString(R.string.label_desc_activation_live_stock, sharedVm.getSubVesselName())
        binding.btnAddActivity.text =
            getString(R.string.label_activate_the_livestock, sharedVm.getSubVesselName())
        return binding.root
    }

    override fun initAction() {
        with(binding) {
            btnAddActivity.setOnClickListener {
                val parser = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val selectedDate: Date? = parser.parse(etCalendar.text)
                selectedDate?.let {
                    viewModel.insertSopDate(
                        InsertSopDateBodyPost(
                            detailSubVessel.id,
                            formatter.format(it)
                        ),
                    )
                }
            }
            ivClose.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun initForm() {
        with(binding) {
            registerFormState(
                etCalendar.initState(),
            )
            etCalendar.addRule(
                etCalendar.state,
                notEmptyRule(
                    getString(
                        R.string.error_empty_field,
                        getString(
                            R.string.hint_add_date,
                            detailSubVessel.activityName
                        )
                    )
                ),
            )
        }
    }

    override fun onAllFormValidated() {
        with(binding) {
            btnAddActivity.isEnable = true
        }
    }

    override fun onFormUnvalidated(view: View, errorMessage: String) {
        with(binding) {
            (view as LgnTextField).error = errorMessage
            btnAddActivity.isEnable = false
        }
    }

    override fun onFormValidated(view: View) {
        (view as LgnTextField).error = ""
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(AddActivityObserver(this, viewModel))
    }

    override fun onPostInsertSopDateLoading() {
        with(binding) {
            btnAddActivity.isLoading = true
        }
    }

    override fun onPostInsertSopDateSuccess(data: Any) {
        lifecycleScope.launch {
            delay(3000)
            dismiss()
            successSnackBar(getString(R.string.label_success_add_activity))
            callback.invoke()
        }
    }

    override fun onPostInsertSopDateFailed(e: Throwable?) {
        with(binding) {
            btnAddActivity.isLoading = false
            e?.message?.let {
                LgnSnackBar.setup(this@AddActivityBottomSheet) {
                    title = it
                    duration = Snackbar.LENGTH_LONG
                    type = LgnSnackBar.ToastType.ERROR
                }.show()
            }
        }
    }

    private val Context.navigationBarHeight: Int
        get() {
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

            return if (Build.VERSION.SDK_INT >= 30) {
                windowManager
                    .currentWindowMetrics
                    .windowInsets
                    .getInsets(WindowInsets.Type.navigationBars())
                    .bottom
            } else {
                val currentDisplay = try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        display
                    } else {
                        windowManager.defaultDisplay
                    }
                } catch (e: NoSuchMethodError) {
                    windowManager.defaultDisplay
                }

                val appUsableSize = Point()
                val realScreenSize = Point()
                currentDisplay?.apply {
                    getSize(appUsableSize)
                    getRealSize(realScreenSize)
                }

                // navigation bar on the side
                if (appUsableSize.x < realScreenSize.x) {
                    return realScreenSize.x - appUsableSize.x
                }

                // navigation bar at the bottom
                return if (appUsableSize.y < realScreenSize.y) {
                    realScreenSize.y - appUsableSize.y
                } else 0
            }
        }

    companion object {
        const val TAG = "AddActivityBottomSheet"
    }
}
