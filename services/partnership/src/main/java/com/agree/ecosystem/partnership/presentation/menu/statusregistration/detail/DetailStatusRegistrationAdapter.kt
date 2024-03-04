package com.agree.ecosystem.partnership.presentation.menu.statusregistration.detail

import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTCTimeTo
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.getSectorColor
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemFinalAssessmentBinding
import com.agree.ecosystem.partnership.databinding.ItemRegistrationStatusDetailsBinding
import com.agree.ecosystem.partnership.databinding.LayoutFinalAssessmentBinding
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.FinalAssessmentStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusTracking
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.setupWith
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.visible
import com.devbase.utils.util.getColorResource
import com.devbase.utils.util.getDrawableResource
import com.devbase.utils.util.getStringResource
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.oratakashi.viewbinding.core.image.load

class DetailStatusRegistrationAdapter : DevRecyclerViewAdapter<RegistrationStatusTracking>() {

    private var finalAssessmentData: List<FinalAssessmentStatus>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<RegistrationStatusTracking> =
        ViewHolder(ItemRegistrationStatusDetailsBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemRegistrationStatusDetailsBinding) :
        DevItemViewHolder<RegistrationStatusTracking>(bindLayout) {
        override fun bind(data: RegistrationStatusTracking?) {
            with(bindLayout) {
                data?.let { tracking ->
                    val context = root.context
                    if (tracking.status == RegistrationStatusTracking.Status.PENDING) {
                        getDrawableResource(R.drawable.ic_status_pending)?.let {
                            ivStatus.load(it)
                        }
                    } else {
                        ivStatus.setColorFilter(
                            tracking.status.textColor.getAttrsValue(context),
                            android.graphics.PorterDuff.Mode.SRC_IN
                        )
                    }
                    tvStatusGroup.apply {
                        text = tracking.name
                        setTextColor(tracking.status.textColor.getAttrsValue(context))
                    }
                    tvDetailStatusTitle.text = tracking.title
                    tvDetailStatusSubtitle.text = tracking.description
                    tvDetailStatusDate.text = tracking.date.convertUTCTimeTo(ConverterDate.FULL_DATE)
                    cvDetailStatus.isVisible = tracking.status != RegistrationStatusTracking.Status.PENDING
                    cvStatus.isVisible = tracking.status == RegistrationStatusTracking.Status.FAILED && tracking.submissionStatus == RegistrationStatus.Status.CANCELED
                    tvStatus.text = tracking.submissionStatus.status
                    layoutRejectedReason.root.isVisible = tracking.status == RegistrationStatusTracking.Status.FAILED && tracking.submissionStatus == RegistrationStatus.Status.REJECTED && absoluteAdapterPosition != listData.lastIndex
                    layoutRejectedReason.tvReason.text = tracking.rejectionReason
                    if (tracking.name == getStringResource(R.string.label_review)) {
                        tvStatusReview.visible()
                        rvSubSectorStatus.apply {
                            val isAllRejected = tracking.subSector.filter { it.scheduleStatus == "rejected" }.size == tracking.subSector.size
                            adapter = SubSectorStatusAdapter(isAllRejected).apply {
                                clear()
                                addOrUpdateAll(tracking.subSector)
                            }
                            setRecycledViewPool(RecyclerView.RecycledViewPool())
                        }
                    }
                    viewVerticalLine.apply {
                        setBackgroundColor(tracking.status.lineColor.getAttrsValue(context))
                        isGone = absoluteAdapterPosition == itemCount - 1
                    }
                    if (absoluteAdapterPosition == listData.lastIndex && (tracking.status == RegistrationStatusTracking.Status.SUCCESS || tracking.status == RegistrationStatusTracking.Status.FAILED)) {
                        setupFinalAssessmentList(this, finalAssessmentData.orEmpty())
                    }
                }
            }
        }
    }

    fun setFinalAssessmentData(data: List<FinalAssessmentStatus>) {
        finalAssessmentData = data
        notifyItemChanged(listData.lastIndex)
    }

    fun setupFinalAssessmentList(
        layoutBinding: ItemRegistrationStatusDetailsBinding,
        data: List<FinalAssessmentStatus>
    ) {
        with(layoutBinding) {
            val context = root.context
            rvFinalAssessment.apply {
                visible()
                setupWith<FinalAssessmentStatus> {
                    withLayoutManager(
                        FlexboxLayoutManager(context).apply {
                            flexDirection = FlexDirection.COLUMN
                        }
                    )
                    withBinding<LayoutFinalAssessmentBinding> { item, binding ->
                        with(binding) {
                            item?.let {
                                layoutRejectedReason.apply {
                                    root.isVisible = !it.status
                                    tvReason.text = it.description
                                }
                                tvAreaName.text = it.vesselName
                                tvFarmArea.text = context.getString(R.string.label_farm_size_value, it.size)
                                tvAreaUsed.text = context.getString(R.string.label_farm_size_value, it.realizationSize)
                                tvLocation.text = it.location
                                setupFinalAssessmentDetail(this, it.subVessels, it.status)
                            }
                        }
                    }
                }.apply {
                    addAll(data)
                }
                setRecycledViewPool(RecyclerView.RecycledViewPool())
            }
        }
    }

    private fun setupFinalAssessmentDetail(
        layoutBinding: LayoutFinalAssessmentBinding,
        data: List<SubVessel>,
        isAreaApproved: Boolean
    ) {
        with(layoutBinding) {
            val context = layoutBinding.root.context
            rvFinalStatus.setupWith<SubVessel> {
                withLayoutManager(
                    FlexboxLayoutManager(context).apply {
                        flexDirection = FlexDirection.COLUMN
                    }
                )
                withBinding<ItemFinalAssessmentBinding> { item, binding ->
                    with(binding) {
                        item?.let {
                            tvSubVessel.text = it.name + " (${it.size}ha)"
                            tvCommodities.text = it.commodityName
                            tvOfftakerName.text = it.workerName
                            layoutRejectedReason.apply {
                                root.isVisible = it.status === SubVessel.Status.INACTIVE && isAreaApproved
                                tvReason.text = it.description
                            }
                            if (it.status === SubVessel.Status.ACTIVE) {
                                tvStatus.apply {
                                    text = getStringResource(R.string.label_accepted)
                                    setTextColor(UiKitAttrs.success_pressed.getAttrsValue(context))
                                }
                                cvStatus.setCardBackgroundColor(UiKitAttrs.success_100.getAttrsValue(context))
                            } else {
                                tvStatus.apply {
                                    text = getStringResource(R.string.label_rejected)
                                    setTextColor(UiKitAttrs.error_pressed.getAttrsValue(context))
                                }
                                cvStatus.setCardBackgroundColor(UiKitAttrs.error_100.getAttrsValue(context))
                            }
                            viewSectorColor.setBackgroundColor(getColorResource(it.sectorName.lowercase().getSectorColor()))
                        }
                    }
                }
            }.apply {
                addAll(data)
            }
            rvFinalStatus.setRecycledViewPool(RecyclerView.RecycledViewPool())
        }
    }
}
