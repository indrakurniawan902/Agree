package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.guidedialog

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.LeadingMarginSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.FragmentGuideDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class GuideDialogFragment(
    val activityName: String,
    val guideContent: String,
    val date: String
) : BottomSheetDialogFragment() {

    private val binding: FragmentGuideDialogBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()

        val content = SpannableStringBuilder()
        val guideContentItems = guideContent.split(";")
        guideContentItems.forEachIndexed { index, guideItem ->
            if (guideItem.isNotEmpty()) {
                if (index == 0) {
                    binding.tvInfo.text = guideItem.trim()
                } else {
                    val contentStart = content.length
                    val leadingString = "$index. "
                    content.append(leadingString)
                    content.append("${guideItem.trim()}\n")

                    val contentEnd = content.length
                    content.setSpan(
                        LeadingMarginSpan.Standard(0, 40),
                        contentStart,
                        contentEnd,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }

        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val locaDate = LocalDate.parse(date.split('T')[0], dtf)
        val defaultZoneId: ZoneId = ZoneId.systemDefault()

        val formattedStringDate = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("id")).format(
            Date.from(
                locaDate.atStartOfDay(
                    defaultZoneId
                )?.toInstant()
            )
        )

        with(binding) {
            tvTitle.text = activityName
            tvDate.text = formattedStringDate
            tvGuideContent.text = content
        }
    }

    private fun initAction() {
        binding.ivClose.setOnClickListener { dismiss() }
        binding.btnCreateRecord.setOnClickListener { dismiss() }
    }
}
