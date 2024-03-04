package com.agree.ecosystem.common.presentation.menu.notifications.detail

import android.content.Intent
import android.net.Uri
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentDetailNotificationBinding
import com.agree.ecosystem.common.databinding.LayoutContainerMessageNotificationBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DetailInbox
import com.agree.ecosystem.common.presentation.menu.notifications.detail.adapters.SubSectorsAdapter
import com.agree.ecosystem.common.presentation.menu.notifications.detail.adapters.SubVesselsAdapter
import com.agree.ecosystem.common.presentation.menu.notifications.detail.adapters.VesselsAdapter
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.common.utils.enums.notification.CategoriesInbox
import com.agree.ecosystem.common.utils.enums.notification.StatesDataDetailInbox
import com.agree.ecosystem.common.utils.enums.notification.StatesInbox
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.presentation.dialog.cs.CustomerServiceDialog
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC3TimeTo
import com.agree.ecosystem.core.utils.utility.convertUTCTimezone
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.partnership.databinding.LayoutRejectedReasonBinding
import com.devbase.utils.util.getColorResource
import com.devbase.utils.util.getDrawableResource
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailNotificationFragment :
    AgrFragment<FragmentDetailNotificationBinding>(),
    DetailNotificationContract {

    private val args: DetailNotificationFragmentArgs by navArgs()

    private lateinit var vsRejectedContainer: LayoutRejectedReasonBinding
    private lateinit var vsContainerMessage: LayoutContainerMessageNotificationBinding

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailNotificationObserver(this, viewModel = viewModel))
        fetchNotificationDetail(args.inboxId)
    }

    override fun initData() {
        super.initData()
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.navController = menuNav.getNavController()
        }
    }

    override fun fetchNotificationDetail(inboxId: String) {
        viewModel.fetchDetailDataNotification(inboxId)
    }

    override fun fetchNotificationDetailOnLoading() {
        binding.msvDetail.showLoadingLayout()
    }

    override fun fetchNotificationDetailOnSuccess(data: DetailInbox) {
        binding.vsContainer.setOnInflateListener { _, view ->
            vsContainerMessage = LayoutContainerMessageNotificationBinding.bind(view)
        }
        binding.vsContainer.inflate()

        data.apply {
            with(this.category) {
                when {
                    contains(CategoriesInbox.PARTNERSHIP.value, true) -> {
                        handlerKemitraan(data)
                        putViewGeneral(data, CategoriesInbox.PARTNERSHIP.value)
                        binding.tvGreeting.text =
                            data.description.replace("Halo, Mitra Agree ", "Halo, Mitra Agree\n\n")
                    }
                    contains(CategoriesInbox.MONITORING.value, true) -> {
                        handlerMonitoring(data)
                        putViewGeneral(data, CategoriesInbox.MONITORING.value)
                        val des = data.description.replace("\\r\\n\\r\\n", "").replace("\n\n", "\n")
                        binding.tvGreeting.text = HtmlCompat.fromHtml(
                            des,
                            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
                        )
                        vsContainerMessage.tvThanks.gone()
                    }
                }
            }
        }
    }

    private fun handlerMonitoring(data: DetailInbox) {
        data.apply {
            with(this.title) {
                when {
                    contains(StatesInbox.MONITORING_TODAY.value, true) -> {
                        val argDate = data.createdAt.substringBefore("+") + "Z"
                        binding.btnRegister.setOnClickListener {
                            menuNav.fromDetailNotificationToHistory(
                                data.subVesselId,
                                argDate.convertUTC3TimeTo(ConverterDate.SQL_DATE)
                                    .substringBefore("•"),
                                StatesInbox.MONITORING_TODAY.value
                            )
                        }
                    }
                    contains(StatesInbox.PAST_MONITORING.value, true) -> {
                        binding.btnRegister.setOnClickListener {
                            menuNav.fromDetailNotificationToHistory(
                                data.subVesselId,
                                "",
                                StatesInbox.PAST_MONITORING.value
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handlerKemitraan(data: DetailInbox) {
        binding.btnRegister.setOnClickListener {
            menuNav.fromDetailNotificationToDetailStatusPartnership(data.submissionId)
        }
        data.apply {
            with(this.status) {
                when {
                    contains(StatesInbox.REGISTRATION.value, true) -> {
                        handlerPartnershipRegistration(
                            data
                        )
                    }
                }
            }
        }
    }

    private fun handlerPartnershipRegistration(data: DetailInbox) {
        data.apply {
            with(data.data.statusDataDetailInbox) {
                when {
                    contains(StatesDataDetailInbox.ASSIGNED.value, true) -> {
                        handlerRegistrationPartnershipReviewed(
                            data,
                            StatesDataDetailInbox.ASSIGNED.value
                        )
                    }
                    contains(StatesDataDetailInbox.REJECTED.value, true) -> {
                        vsContainerMessage.vsRejectedReason.setOnInflateListener { _, view ->
                            vsRejectedContainer = LayoutRejectedReasonBinding.bind(view)
                        }
                        vsContainerMessage.vsRejectedReason.inflate()
                        handlerRegistrationPartnershipRejected(
                            data
                        )
                    }
                    contains(StatesDataDetailInbox.SURVEYED.value, true) -> {
                        handlerRegistrationPartnershipSurveyed(data)
                    }
                    contains(StatesDataDetailInbox.APPROVED.value, true) -> {
                        handlerRegistrationPartnershipApproved(data)
                    }
                }
            }
        }
    }

    private fun handlerRegistrationPartnershipApproved(data: DetailInbox) {
        binding.apply {
            putViewAdapterGeneral(data)
            vsContainerMessage.rvLastResult.visible()
            vesselsAdapter.addOrUpdateAll(data.data.vesselsDataDetailInbox)
        }
    }

    private fun handlerRegistrationPartnershipSurveyed(data: DetailInbox) {
        binding.apply {
            putViewAdapterGeneral(data)
        }
    }

    private fun handlerRegistrationPartnershipRejected(
        data: DetailInbox
    ) {
        if (data.data.titleDataDetailInbox.contains(StatesInbox.LAST_REVIEW_DONE.value, true)) {
            binding.apply {
                putViewAdapterGeneral(data)
                vsContainerMessage.rvLastResult.visible()
                vesselsAdapter.addOrUpdateAll(data.data.vesselsDataDetailInbox)
            }
        } else if (data.data.titleDataDetailInbox.contains(
                StatesInbox.REJECTED_REGISTRATION.value,
                true
            )
        ) {
            binding.apply {
                putViewAdapterGeneral(data)
                vsRejectedContainer.apply {
                    cvReasonRejected.visible()
                    tvReason.text = data.data.dataRejected.description
                }
                vsContainerMessage.tvMessageResult.setTextColor(getColorResource(com.agree.ui.R.color.error_500))
            }
        }
    }

    private fun handlerRegistrationPartnershipReviewed(data: DetailInbox, type: String) {
        binding.apply {
            putViewAdapterGeneral(data)
            vsContainerMessage.tvStatusReview.visible()
            vsContainerMessage.rvSubSectorStatus.visible()
            with(type) {
                when {
                    contains(
                        StatesDataDetailInbox.ASSIGNED.value,
                        true
                    ) -> subSectorAdapter.addOrUpdateAll(data.data.subSectorDataDetailInbox)
                }
            }
        }
    }

    private fun putViewAdapterGeneral(data: DetailInbox) {
        binding.apply {
            vsContainerMessage.apply {
                tvMessageResult.text = data.data.titleDataDetailInbox
                tvMessageDescription.text = data.data.descriptionDataDetailInbox
                rvSubSectorStatus.adapter = subSectorAdapter
                rvLastResult.adapter = vesselsAdapter
            }
        }
    }

    private fun putViewGeneral(data: DetailInbox, category: String) {
        with(binding) {
            msvDetail.showDefaultLayout()
            vsContainerMessage.apply {
                containerToCs.ivIconToCs.apply {
                    setImageDrawable(getDrawableResource(R.drawable.ic_background_circle_blue))
                    containerToCs.container.setOnClickListener {
                        CustomerServiceDialog {
                            sendMessage()
                        }.showNow(childFragmentManager, "dialog")
                    }
                }
            }

            tvDetailTitle.text = data.category
            val dateTime = data.createdAt.convertUTCTimezone(ConverterDate.FULL_DATE_TIME, false)
            tvDate.text = dateTime
            tvSubTitle.text = data.title
            with(category) {
                when {
                    contains(CategoriesInbox.PARTNERSHIP.value, true) -> {
                        tvGreeting.text =
                            data.description.replace("Halo, Mitra Agree ", "Halo, Mitra Agree\n\n")
                    }
                    contains(CategoriesInbox.MONITORING.value, true) -> {
                        tvGreeting.text =
                            HtmlCompat.fromHtml(
                                data.description,
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            ).toString().replace("\\n\\n", "\n")
                        vsContainerMessage.apply {
                            this.cvContainer.gone()
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun sendMessage() {
        val phone =
            if (prefs.contactInfo == "") "6281280006756"
            else prefs.contactInfo.replace("08", "628")
        val url = "https://api.whatsapp.com/send?phone=$phone"

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private val subSectorAdapter = SubSectorsAdapter()
    private val vesselsAdapter = VesselsAdapter(SubVesselsAdapter())
    private val viewModel: DetailNotificationViewModel by viewModel()
    private val menuNav: MenuNavigation by navigation { activity }
    private val prefs: AgrPrefUsecase by inject()
}
