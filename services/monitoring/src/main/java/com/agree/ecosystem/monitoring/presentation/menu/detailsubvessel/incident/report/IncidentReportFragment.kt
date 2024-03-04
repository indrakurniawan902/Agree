package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrChildFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.toCurrencyFormat
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.AddIncidentCommentBodyPost
import com.agree.ecosystem.monitoring.databinding.FragmentIncidentReportBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment.IncidentComment
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ui.UiKitAttrs
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncidentReportFragment :
    AgrChildFragment<FragmentIncidentReportBinding>(),
    IncidentReportDataContract {

    override fun initUI() {
        super.initUI()
        initComment()
        with(binding) {
            adapter = IncidentReportAdapter(requireActivity())
            rvComment.adapter = adapter
            firstData()

            containerDetail.setOnClickListener {
                detailSubVesselNav.fromIncidentReportToIncidentDetail(args.incident)
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(IncidentReportObserver(this, viewModel))
        viewModel.getListIncidentComment(args.incident?.activityId.toString())
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    @SuppressLint("CutPasteId")
    private fun initComment() {
        with(binding.etComment) {
//            val params =
//                instanceEditText.findViewById<ImageButton>(com.telkom.legion.component.R.id.ivEndIconDrawable)
//                    .layoutParams as LinearLayout.LayoutParams
//            params.gravity = Gravity.BOTTOM
//            params.bottomMargin = 10
//            params.topMargin = 10
//            params.marginEnd = 10
//            params.marginStart = 30
//            instanceEditText
//                .findViewById<ImageButton>(com.telkom.legion.component.R.id.ivEndIconDrawable).layoutParams =
//                params
            instanceEditText.setEndIconTintMode(PorterDuff.Mode.MULTIPLY)
            instanceEditText.setEndIconTintList(
                ContextCompat.getColorStateList(
                    requireContext(),
                    android.R.color.white
                )!!
            )
            instanceEditText.maxLines = 5
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Do Nothing
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val comment = p0?.trim().toString()
                    val drawable = if (comment.isEmpty()) {
                        isButtonEnable = false
                        R.drawable.ic_send_disabled
                    } else {
                        isButtonEnable = true
                        R.drawable.ic_send_enabled
                    }
                    endIconDrawable =
                        ContextCompat.getDrawable(
                            requireContext(),
                            drawable
                        )
                }

                override fun afterTextChanged(p0: Editable?) {
                    // Do Nothing
                }
            })
            instanceEditText.setEndIconOnClickListener {
                if (isButtonEnable) {
                    DialogUtils.showCustomDialog(
                        context = requireContext(),
                        title = getString(R.string.label_confirmation),
                        message = getString(R.string.label_comment_confirmation),
                        positiveAction = Pair(getString(R.string.action_yes_send)) {
                            val message = editText?.text.toString().replace("\n", "</br>")
                            val incident = args.incident
                            val data = AddIncidentCommentBodyPost(
                                activityEventId = incident?.activityId.toString(),
                                commenterId = prefs.userId,
                                commenterName = prefs.name,
                                commenterType = "User",
                                message = message
                            )
                            viewModel.addIncidentComment(data)
                            setInputCommentEnable(false)
                        },
                        negativeAction = Pair(getString(R.string.action_cancel), null),
                        autoDismiss = false
                    )
                }
            }
        }
    }

    private fun firstData(): List<IncidentComment> {
        val data = mutableListOf<IncidentComment>()
        val incident = args.incident
        val expenditure = if (incident?.expenditure?.isEmpty() == true) "0" else incident?.expenditure
        val cost = (expenditure?.toDouble() ?: 0.0).toCurrencyFormat()
        val newLine = "</br>"
        val category = if (incident?.category.toString().isNotEmpty()) incident?.category.plus(newLine) else ""
        val name = if (incident?.name.toString().isNotEmpty()) incident?.name.plus(newLine) else ""
        val population = if (incident?.population.toString().isNotEmpty()) requireContext().getString(R.string.label_affected_population).plus(" ").plus(incident?.population).plus(" ").plus(newLine) else ""
        val actionTaken = if (incident?.actionTaken.toString().isNotEmpty()) incident?.actionTaken.plus(newLine) else ""
        val costOperational = if (incident?.expenditure.toString().isNotEmpty()) requireContext().getString(R.string.label_operating_cost).plus(": ").plus(requireContext().getString(R.string.label_rupiah_value, cost)) else ""

        data.add(
            IncidentComment(
                id = "",
                activityId = args.incident?.activityId.toString(),
                commenterId = prefs.userId,
                commenterName = prefs.name,
                createdAt = incident?.dateTime.toString(),
                message = category.plus(name).plus(population).plus(actionTaken).plus(costOperational),
                commenterType = "user",
                images = incident?.data?.images ?: listOf()
            )
        )
        adapter.addOrUpdateAll(data)
        return data
    }

    override fun initData() {
        super.initData()
        with(binding) {
            val incident = args.incident
            tvIncidentType.text = incident?.category
            tvIncidentType.visibility = if (incident?.category.toString().isNotEmpty()) View.VISIBLE else View.GONE
            tvIncident.text = incident?.name
            tvIncident.visibility = if (incident?.name.toString().isNotEmpty()) View.VISIBLE else View.GONE
            tvTicketNumber.text = getString(R.string.label_ticket_number, incident?.activityCode)
            tvTicketNumber.visibility = if (incident?.activityCode.toString().isNotEmpty()) View.VISIBLE else View.GONE

            if (incident?.status == requireContext().getString(R.string.label_pending) || incident?.status == requireContext().getString(
                    R.string.label_processed
                )
            ) {
                tvStatus.apply {
                    text = context.getString(R.string.label_inprogress)
                    setTextColor(UiKitAttrs.warning_normal.getAttrsValue(requireContext()))
                }
                tvLabelIncidentClosed.gone()
                etComment.visible()
            } else {
                tvStatus.apply {
                    text = context.getString(R.string.label_done)
                    setTextColor(UiKitAttrs.success_normal.getAttrsValue(requireContext()))
                }
                tvLabelIncidentClosed.visible()
                etComment.gone()
            }
        }
    }

    override fun onAddIncidentCommentLoading() {
        // Do Nothing
    }

    override fun onAddIncidentCommentSuccess(data: IncidentComment) {
        with(binding) {
            setInputCommentEnable()
            etComment.editText?.setText("")
            setDataIncidents(listOf(data))
            Handler(Looper.getMainLooper()).postDelayed({
                containerContent.post {
                    containerContent.fullScroll(View.FOCUS_DOWN)
                }
            }, 1000)
        }
    }

    override fun onAddIncidentCommentFailed(e: Throwable?) {
        binding.msvContent.showErrorLayout()
    }

    override fun onAddIncidentCommentEmpty(e: Throwable?) {
        with(binding) {
            msvContent.showDefaultLayout()
            containerBottom.visible()
        }
    }

    override fun onGetIncidentCommentLoading() {
        with(binding) {
            msvContent.showLoadingLayout()
            containerBottom.gone()
        }
    }

    override fun onGetIncidentCommentFailed(e: Throwable?) {
        binding.msvContent.showErrorLayout()
    }

    override fun onGetIncidentCommentEmpty(e: Throwable?) {
        setDataIncidents()
    }

    override fun onGetIncidentCommentSuccess(data: List<IncidentComment>) {
        setDataIncidents(data)
    }

    private fun setDataIncidents(data: List<IncidentComment> = listOf()) {
        with(binding) {
            msvContent.showDefaultLayout()
            containerBottom.visible()
            if (data.isNotEmpty()) adapter.addOrUpdateAll(data)
            setInputCommentEnable()
        }
    }

    private fun setInputCommentEnable() {
        val enableBottom = adapter.listData[adapter.listData.size - 1]?.commenterType.toString()
            .lowercase() != "user"
        setInputCommentEnable(enableBottom)
    }

    private fun setInputCommentEnable(isEnable: Boolean) {
        binding.etComment.isEnabled = isEnable
        binding.etComment.editText?.isEnabled = isEnable
    }

    private val detailSubVesselNav: DetailSubVesselNavigation by navigation { activity }
    private var isButtonEnable = false
    private lateinit var adapter: IncidentReportAdapter
    private val viewModel: IncidentReportViewModel by viewModel()
    private val args: IncidentReportFragmentArgs by navArgs()
    private val prefs: AgrPrefUsecase by inject()
}
