package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.detail

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrChildFragment
import com.agree.ecosystem.core.utils.presentation.dialog.previewimage.PreviewImageFragment
import com.agree.ecosystem.core.utils.utility.CircleEdgeTreatment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTCTimezone
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.toCurrencyFormat
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentIncidentDetailBinding
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.PhotoAdapter
import com.agree.ui.UiKitAttrs
import com.devbase.utils.ext.gone
import com.oratakashi.viewbinding.core.tools.visible

class IncidentDetailFragment : AgrChildFragment<FragmentIncidentDetailBinding>() {
    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun initData() {
        super.initData()
        with(binding) {
            val incident = args.incident
            tvDateTime.text =
                incident?.dateTime?.convertUTCTimezone(ConverterDate.SHORT_DATE, visibleTimeZone = false)

            if (incident?.status == requireContext().getString(R.string.label_pending) || incident?.status == requireContext().getString(
                    R.string.label_processed
                )
            ) {
                tvStatus.apply {
                    text = context.getString(R.string.label_inprogress)
                    setTextColor(UiKitAttrs.warning_normal.getAttrsValue(requireContext()))
                }
                cvStatus.strokeColor = UiKitAttrs.warning_normal.getAttrsValue(requireContext())
            } else {
                tvStatus.apply {
                    text = context.getString(R.string.label_done)
                    setTextColor(UiKitAttrs.success_normal.getAttrsValue(requireContext()))
                }
                cvStatus.strokeColor = UiKitAttrs.success_hover.getAttrsValue(requireContext())
            }
            tvIncidentType.text =
                if (incident?.category.toString().isNotEmpty()) incident?.category else "-"
            tvIncident.text = if (incident?.name.toString().isNotEmpty()) incident?.name else "-"
            tvAffected.text = if (incident?.population.toString().isNotEmpty())
                "${incident?.population}" else "-"
            tvEffort.text =
                if (incident?.actionTaken.toString().isNotEmpty()) incident?.actionTaken else "-"
            val expenditure =
                if (incident?.expenditure?.isEmpty() == true) "0" else incident?.expenditure
            tvCost.text = requireContext().getString(
                R.string.label_rupiah_value,
                (expenditure?.toDouble() ?: 0.0).toCurrencyFormat()
            )
            tvNote.text =
                if (incident?.description.toString().isNotEmpty()) incident?.description else "-"

            val images = incident?.data?.images
            if ((images?.size ?: 0) > 0) {
                containerPhoto.visible()
                adapter.addOrUpdateAll(images)
                rvImages.adapter = adapter
            } else {
                val sizeInDp = 30
                val scale = resources.displayMetrics.density
                val dpAsPixels = (sizeInDp * scale + 0.5f)
                containerNote.setPadding(0, 0, 0, dpAsPixels.toInt())
                containerPhoto.gone()
            }
        }
    }

    override fun initUI() {
        super.initUI()
        applyEdgeTreatmentToCardView()
    }

    private fun applyEdgeTreatmentToCardView() {
        with(binding) {
            cvCard.shapeAppearanceModel = cvCard.shapeAppearanceModel.toBuilder()
                .setBottomEdge(CircleEdgeTreatment(25f, true, 10))
                .setTopLeftCornerSize(20f)
                .setTopRightCornerSize(20f)
                .build()
        }
    }

    private val args: IncidentDetailFragmentArgs by navArgs()
    private val adapter = PhotoAdapter {
        val images = args.incident?.data?.images
        if ((images?.size ?: 0) > 0) {
            PreviewImageFragment(images = images ?: listOf(), it)
                .setBackgroundColor(com.agree.ui.R.color.tertiary_700)
                .setToolbarCloseTitle(requireActivity().resources.getString(R.string.label_close))
                .show(
                    requireActivity().supportFragmentManager,
                    PreviewImageFragment.TAG
                )
        }
    }
}
